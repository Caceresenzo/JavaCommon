package caceresenzo.libs.cryptography.drm;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import caceresenzo.libs.cryptography.drm.DRMServer.IRequestReply.ReplyType;
import caceresenzo.libs.stream.StreamUtils;

public class DRMServer extends Decryptable {

	ServerSocket mServerSocket;
	int mBindingPort;
	WorkerThread mWorkerThread;
	AESEncryptKeys mKeys;

	String mRootDir;

	public DRMServer(String hostBookPath, AESEncryptKeys keys, int bindingPort) {
		mKeys = keys;
		mBindingPort = bindingPort;
		mRootDir = hostBookPath + "/OEBPS";
	}

	public int getBindingPort() {
		return mBindingPort;
	}

	static HashMap<String, IRequestReply> mDesignatedReplyMap = new HashMap<String, IRequestReply>();

	interface IRequestReply {
		enum ReplyType {
			PlainText, JavaScript, JPG, PNG
		};

		Object getData();

		InputStream getDataStream();

		long getDataLength();

		ReplyType getType();
	}

	static private class RequestReplyFile implements IRequestReply {

		File mFile;

		RequestReplyFile(File f) {
			mFile = f;
		}

		@Override
		public File getData() {
			return mFile;
		}

		@Override
		public FileInputStream getDataStream() {
			try {
				return new FileInputStream(mFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Error creating FileStream");
			}
		}

		@Override
		public long getDataLength() {
			return mFile.length();
		}

		@Override
		public ReplyType getType() {
			String identifier = mFile.getName().toLowerCase();
			if (identifier.contains(".jpg") || identifier.contains(".jpeg"))
				return ReplyType.JPG;
			else if (identifier.contains(".png"))
				return ReplyType.PNG;
			else if (identifier.contains("epubjs"))
				return ReplyType.JavaScript;

			return ReplyType.PlainText;
		}

	}

	static private class RequestReplyString implements IRequestReply {

		String mString;
		ReplyType mType;

		RequestReplyString(String s, ReplyType type) {
			mString = s;
			mType = type;
		}

		@Override
		public String getData() {
			return mString;
		}

		@Override
		public ByteArrayInputStream getDataStream() {
			try {
				return new ByteArrayInputStream(mString.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException("error encoding, maybe caused by old platform that do not support utf-8");
			}
		}

		@Override
		public long getDataLength() {
			if (mString == null)
				return 0;
			return mString.length();
		}

		@Override
		public ReplyType getType() {
			return mType;
		}

	}

	public void setDesignatedRequestReply(String specifiedRequest, String reply) {
		String request = specifiedRequest.toLowerCase();
		if (specifiedRequest.startsWith("/") == false)
			request = "/" + request;
		mDesignatedReplyMap.put(request, new RequestReplyString(reply, request.contains("epubjs") ? ReplyType.JavaScript : ReplyType.PlainText));
	}

	public void setDesignatedRequestReply(String specifiedRequest, File replyFileHandle) {
		String request = specifiedRequest.toLowerCase();
		if (specifiedRequest.startsWith("/") == false)
			request = "/" + request;
		mDesignatedReplyMap.put(request, new RequestReplyFile(replyFileHandle));
	}

	public void setDesignatedRequestIgnore(String specifiedRequest) {
		String request = specifiedRequest.toLowerCase();
		if (specifiedRequest.startsWith("/") == false)
			request = "/" + request;
		mDesignatedReplyMap.put(request, null);
	}

	static final String CRLF = "\r\n";

	public int startServer() throws IOException {
		if (mWorkerThread != null)
			return mBindingPort;

		mWorkerThread = new WorkerThread();
		mWorkerThread.start();
		return mBindingPort;
	}

	public void terminateServer() {
		if (mWorkerThread == null)
			return;

		try {
			mWorkerThread.terminate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mWorkerThread = null;
	}

	class WorkerThread extends Thread {
		boolean mIsRunning = true;

		WorkerThread() throws IOException {

			mServerSocket = new ServerSocket();
			mServerSocket.bind(new InetSocketAddress(mBindingPort));
		}

		public void terminate() throws IOException {
			mIsRunning = false;
			mServerSocket.close();
		}

		@Override
		public void run() {
			while (mIsRunning) {
				try {
					Socket acceptSocket = mServerSocket.accept();

					// logD("Socket Accepted.");

					InputStream is = acceptSocket.getInputStream();
					OutputStream os = acceptSocket.getOutputStream();

					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					processReader(reader, os);

					is.close();
					os.close();
					reader.close();
					acceptSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

		private void parseGetCommand(String commandToken, OutputStream os) throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {

			// logD("Command Token : " + commandToken);
			// ÂÖàÊ™¢Êü•ÊòØÂê¶Âú®mDesignatedReplyMapË£°Èù¢
			if (mDesignatedReplyMap.containsKey(commandToken.toLowerCase()) == true) {

				// logD("Exclusive token : " + commandToken);
				IRequestReply request = mDesignatedReplyMap.get(commandToken.toLowerCase());
				if (request == null)
					return;

				generateFileHeader(request, os);
				writeToOutput(request.getDataStream(), os);
				return;
			}

			File file = new File(mRootDir + commandToken);
			if (file.exists() == false) {

				// Log.e("DRMServer", "file " + file.getAbsolutePath() + " is not found!");

				invokeHttpError404(os);
				return;
			}

			generateFileHeader(file, os);
			writeToOutput(createDecryptStream(file), os);

		}

		private void writeToOutput(InputStream is, OutputStream os) throws IOException {
			byte[] buffer = new byte[8092];
			int lenght;
			while ((lenght = is.read(buffer)) > 0) {
				os.write(buffer, 0, lenght);
			}
		}

		private void generateFileHeader(File file, OutputStream os) throws IOException {
			StringBuilder sb = new StringBuilder();

			sb.append("HTTP/1.1 200 OK" + CRLF);

			String contentTypeLine;
			String fileName = file.getName();

			if (fileName.toLowerCase().contains(".jpeg") || fileName.toLowerCase().contains(".jpg"))
				contentTypeLine = "image/jpeg" + CRLF;
			else if (fileName.toLowerCase().contains(".png"))
				contentTypeLine = "image/png" + CRLF;
			else
				contentTypeLine = "text/html" + CRLF;

			sb.append("Keep-Alive: timeout=15, max=500" + CRLF);
			sb.append("Connection: Keep-Alive" + CRLF);
			sb.append("Content-Type: " + contentTypeLine);
			sb.append("Content-Length: " + file.length() + CRLF);

			// logD("server response : " + sb.toString());

			sb.append(CRLF);
			os.write(sb.toString().getBytes());

		}

		private void generateFileHeader(IRequestReply r, OutputStream os) throws IOException {
			StringBuilder sb = new StringBuilder();

			String contentTypeLine;
			sb.append("HTTP/1.1 200 OK" + CRLF);
			if (r.getType() == ReplyType.JPG)
				contentTypeLine = "image/jpeg" + CRLF;
			else if (r.getType() == ReplyType.PNG)
				contentTypeLine = "image/png" + CRLF;
			else if (r.getType() == ReplyType.JavaScript)
				contentTypeLine = "text/javascript" + CRLF;
			else
				contentTypeLine = "text/html" + CRLF;

			sb.append("Keep-Alive: timeout=15, max=500" + CRLF);
			sb.append("Connection: Keep-Alive" + CRLF);

			sb.append("Content-Type: " + contentTypeLine);
			sb.append("Content-Length: " + r.getDataLength() + CRLF);
			sb.append(CRLF);

			// logD("server response : " + sb.toString());

			os.write(sb.toString().getBytes());

		}

		public String getDecryptedContent(String contentName) throws FileNotFoundException, IOException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
			File file = new File(mRootDir + contentName);
			if (file.exists() == false) {
				return null;
			}

			return StreamUtils.InputStreamToString(createDecryptStream(file));
		}

		private void invokeHttpError404(OutputStream os) throws IOException {
			os.write(("HTTP/1.0 404 Not Found" + CRLF).getBytes());
		}

		public void processWriteToClient(OutputStream os) {

		}

		public void processReader(BufferedReader reader, OutputStream os) throws IOException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException {
			String command = "null command";
			while (command != null && command.equals("") == false && command.equals(CRLF) == false) {
				command = reader.readLine();

				if (command == null)
					return;

				StringTokenizer st = new StringTokenizer(command);
				String header = "";
				if (st.hasMoreTokens())
					header = st.nextToken();

				if (header.equalsIgnoreCase("GET"))
					parseGetCommand(st.nextToken(), os);
			}

		}

	}

	@Override
	public AESEncryptKeys getAESKeys() {
		return mKeys;
	}

}