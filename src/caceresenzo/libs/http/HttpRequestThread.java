package caceresenzo.libs.http;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import caceresenzo.libs.bytes.ByteFormat;
import caceresenzo.libs.http.content.ContentIndex;
import caceresenzo.libs.http.exception.HttpHookRegistry;
import caceresenzo.libs.http.exception.HttpHookRegistry.HookRegistryMatch;
import caceresenzo.libs.regex.RegexUtils;
import caceresenzo.libs.http.exception.HttpRequestException;
import caceresenzo.libs.string.SimpleLineStringBuilder;
import caceresenzo.libs.url.UrlUtils;

/**
 * A thread which deals with an individual request to the web server. This is passed a socket from the WebServer when a connection is accepted.
 */
public class HttpRequestThread implements Runnable {
	
	public static final Comparator<File> FILECOMPARATOR = new Comparator<File>() {
		@Override
		public int compare(File file1, File file2) {
			Collator collator = Collator.getInstance();
			
			if (file1.isDirectory() && file2.isFile()) {
				return -1;
			} else if (file1.isFile() && file2.isDirectory()) {
				return +1;
			} else {
				return collator.compare(file1.getName(), file2.getName());
			}
		}
	};
	
	private HttpServer server;
	private Socket socket;
	private File rootDirectory;
	
	public HttpRequestThread(HttpServer server, Socket socket, File rootDirectory) {
		this.server = server;
		this.socket = socket;
		this.rootDirectory = rootDirectory;
	}
	
	public void run() {
		HttpRequest httpRequest = null;
		try {
			httpRequest = new HttpRequest(server, socket);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		
		if (httpRequest == null) {
			return;
		}
		
		try {
			String path = httpRequest.getRequestedPath();
			HashMap<String, String> parameters = UrlUtils.parseParameters(path, true);
			String ressource = UrlUtils.parseRessource(path);
			
			File file = new File(rootDirectory, ressource);
			file = file.getCanonicalFile();
			
			if (server.getMode().isHooksAllowed()) {
				HttpHookRegistry hookRegistry = server.getServerConfig().getHookRegistry();
				
				HookRegistryMatch hookRegistryMatch = hookRegistry.match(path);
				HttpHook hook = hookRegistryMatch.getHook();
				
				if (hook != null) {
					hook.call(hookRegistryMatch.getMatcher(), path, ressource, parameters, RegexUtils.getGroup(hookRegistryMatch.getMatcher()));
					httpRequest.output(hook.code(), hook.content(), hook.mime()).send();
					HttpServerLogger.log(httpRequest.getIp(), httpRequest.getRequest(), hook.code());
					return;
				}
			}
			
			if (rootDirectory == null || !server.getMode().isExplorerAllowed()) {
				HttpServerLogger.log(httpRequest.getIp(), httpRequest.getRequest(), 403);
				httpRequest.error(403, "<h1>403 Forbidden</h1> <h6>debug: " + server.getMode().toString() + "</h6><code>" + path + "</code>").send();
				return;
			}
			
			if (!file.toString().startsWith(rootDirectory.toString())) { // Outside of the main directory
				HttpServerLogger.log(httpRequest.getIp(), httpRequest.getRequest(), 403);
				httpRequest.error(403, "<h1>403 Forbidden</h1><code>" + path + "</code>").send();
				return;
			}
			
			if (file.isDirectory()) { // Check to see if there are any index files in the directory.
				for (int i = 0; i < HttpServerConstants.DEFAULT_FILES.length; i++) {
					File indexFile = new File(file, HttpServerConstants.DEFAULT_FILES[i]);
					if (indexFile.exists() && !indexFile.isDirectory()) {
						file = indexFile;
						break;
					}
				}
				if (file.isDirectory()) { // Print directory listing
					HttpServerLogger.log(httpRequest.getIp(), httpRequest.getRequest(), 200);
					if (!path.endsWith("/")) {
						path = path + "/";
					}
					File[] files = file.listFiles();
					
					SimpleLineStringBuilder builder = new SimpleLineStringBuilder("", HttpServer.LINE_SEPARATOR);
					builder.appendln("<html>");
					builder.appendln("<head>");
					builder.appendln("	<title>Index of: " + path + "</title>");
					builder.appendln("	<style>");
					builder.appendln(ContentIndex.readFileStream("indexer", "style.css"));
					builder.appendln("	</style>");
					builder.appendln("</head>");
					builder.appendln("<body>");
					builder.appendln("	<h1> <span class=\"blue\">&lt;</span>Indexer<span class=\"blue\">&gt;</span><br><span class=\"yellow\">" + path + "</span></h1>");
					
					builder.appendln("	<table class=\"container\">");
					builder.appendln("		<thead>");
					builder.appendln("			<tr>");
					builder.appendln("				<th><h1>Name</h1></th>");
					builder.appendln("				<th><h1>Size</h1></th>");
					builder.appendln("				<th><h1>Last Modified</h1></th>");
					builder.appendln("				<th><h1>Type</h1></th>");
					builder.appendln("			</tr>");
					builder.appendln("		</thead>");
					builder.appendln("		<tbody>");
					builder.appendln("			<tr>");
					if (!path.equals("/")) {
						builder.appendln("				<td><b><u><a href=\"" + path + "../\">Parent directory ../</a></u></b></td>");
						builder.appendln("				<td>-/-</td>");
						builder.appendln("				<td>-/-</td>");
						builder.appendln("				<td>-/-</td>");
					}
					builder.appendln("			</tr>");
					
					Arrays.sort(files, FILECOMPARATOR);
					
					for (int i = 0; i < files.length; i++) {
						file = files[i];
						builder.appendln("			<tr>");
						
						if (file.isDirectory()) {
							builder.appendln("				<td><b><a href=\"" + path + file.getName() + "/\">" + file.getName() + "/</a></b></td>");
							builder.appendln("				<td>-/-</td>");
							builder.appendln("				<td>-/-</td>");
							builder.appendln("				<td>FOLDER</td>");
						} else {
							builder.appendln("				<td><a href=\"" + path + file.getName().replace("\\/", "") + "\">" + file.getName() + "</a></td>");
							builder.appendln("				<td>" + ByteFormat.toHumanBytes(file.length(), 2) + "</td>");
							builder.appendln("				<td>" + new Date(file.lastModified()).toString() + "</td>");
							builder.appendln("				<td>FILE</td>");
						}
						
						builder.appendln("			</tr>");
					}
					
					builder.appendln("		</tbody>");
					builder.appendln("	</table>");
					builder.appendln("	<h1><span class=\"blue\">&lt;/</span>Indexer<span class=\"blue\">&gt;</span></h1>");
					builder.appendln("	<h2>" + HttpServerConstants.VERSION + "</h2>");
					builder.appendln("</body>");
					builder.appendln("</html>");
					
					httpRequest.output(builder.toString()).send();
					return;
				}
			}
			
			if (!file.exists()) { // The file was not found.
				HttpServerLogger.log(httpRequest.getIp(), httpRequest.getRequest(), 404);
				httpRequest.error(404, "<h1>404 File Not Found</h1><code>" + path + "</code>").send();
				return;
			}
			
			if (file.getParent().indexOf("cgi-bin") >= 0) { // Execute any files in any cgi-bin directories under the web root.
				try {
					httpRequest.executeServerSideScriptEngine(file, path);
				} catch (Throwable throwable) { // Internal server error!
					HttpServerLogger.log(httpRequest.getIp(), httpRequest.getRequest(), 500);
					httpRequest.error(404, "<h1>500 Internal Server Error</h1><code>" + path + "</code><hr>Your script produced the following error: -<p><pre>" + throwable.toString() + "</pre></code>").send();
					return;
				}
				
				HttpServerLogger.log(httpRequest.getIp(), path, 200);
				return;
			}
			
			HttpServerLogger.log(httpRequest.getIp(), httpRequest.getRequest(), 200);
			httpRequest.output(200).sendFile(file);
		} catch (IOException | HttpRequestException exception) {
			HttpServerLogger.log(httpRequest.getIp(), "ERROR " + exception.toString() + " " + httpRequest.getRequest(), 0);
			exception.printStackTrace();
		}
	}
	
}