package caceresenzo.libs.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpExtensionRegistry {
	
	private HashMap<String, String> mimes = new HashMap<>();
	private List<String> ssis = new ArrayList<String>();
	
	public HttpExtensionRegistry(HashMap<String, String> defaultMime) {
		mimes.putAll(defaultMime);
	}
	
	public HttpExtensionRegistry(boolean includeDefault) {
		if (includeDefault) {
			loadDefaultMime();
			loadDefaultSsi();
		}
	}
	
	public HashMap<String, String> getMimeExtensions() {
		return mimes;
	}
	
	public void putMime(String extension, String mime) {
		if (!extension.startsWith("\\.")) {
			mime += "." + mime;
		}
		mimes.put(extension, mime);
	}
	
	public List<String> getSsiExtensions() {
		return ssis;
	}
	
	/**
	 * Set up the filename extension to mime type associations.
	 */
	public void loadDefaultMime() {
		mimes.put(".ai", ExtensionMime.PS.getMime());
		mimes.put(".ps", ExtensionMime.PS.getMime());
		mimes.put(".eps", ExtensionMime.PS.getMime());
		
		mimes.put(".rtf", ExtensionMime.RTF.getMime());
		
		mimes.put(".au", ExtensionMime.AUDIO.getMime());
		mimes.put(".snd", ExtensionMime.AUDIO.getMime());
		
		mimes.put(".bin", ExtensionMime.OCTETSTREAM.getMime());
		mimes.put(".dms", ExtensionMime.OCTETSTREAM.getMime());
		mimes.put(".lha", ExtensionMime.OCTETSTREAM.getMime());
		mimes.put(".lzh", ExtensionMime.OCTETSTREAM.getMime());
		mimes.put(".exe", ExtensionMime.OCTETSTREAM.getMime());
		mimes.put(".class", ExtensionMime.OCTETSTREAM.getMime());
		
		mimes.put(".doc", ExtensionMime.DOC.getMime());
		
		mimes.put(".pdf", ExtensionMime.PDF.getMime());
		
		mimes.put(".ppt", ExtensionMime.PPT.getMime());
		
		mimes.put(".smi", ExtensionMime.SMI.getMime());
		mimes.put(".smil", ExtensionMime.SMI.getMime());
		mimes.put(".sml", ExtensionMime.SMI.getMime());
		
		mimes.put(".js", ExtensionMime.JS.getMime());
		
		mimes.put(".zip", ExtensionMime.ZIP.getMime());
		
		mimes.put(".midi", ExtensionMime.MIDI.getMime());
		mimes.put(".kar", ExtensionMime.MIDI.getMime());
		
		mimes.put(".mpga", ExtensionMime.MP3.getMime());
		mimes.put(".mp2", ExtensionMime.MP3.getMime());
		mimes.put(".mp3", ExtensionMime.MP3.getMime());
		
		mimes.put(".wav", ExtensionMime.WAV.getMime());
		
		mimes.put(".gif", ExtensionMime.GIF.getMime());
		
		mimes.put(".ief", ExtensionMime.IEF.getMime());
		
		mimes.put(".jpeg", ExtensionMime.JPEG.getMime());
		mimes.put(".jpg", ExtensionMime.JPEG.getMime());
		mimes.put(".jpe", ExtensionMime.JPEG.getMime());
		
		mimes.put(".png", ExtensionMime.PNG.getMime());
		
		mimes.put(".tiff", ExtensionMime.TIFF.getMime());
		mimes.put(".tif", ExtensionMime.TIFF.getMime());
		
		mimes.put(".wrl", ExtensionMime.VRML.getMime());
		mimes.put(".vrml", ExtensionMime.VRML.getMime());
		
		mimes.put(".css", ExtensionMime.CSS.getMime());
		
		mimes.put(".html", ExtensionMime.HTML.getMime());
		mimes.put(".htm", ExtensionMime.HTML.getMime());
		mimes.put(".shtml", ExtensionMime.HTML.getMime());
		mimes.put(".shtm", ExtensionMime.HTML.getMime());
		mimes.put(".stm", ExtensionMime.HTML.getMime());
		mimes.put(".sht", ExtensionMime.HTML.getMime());
		
		mimes.put(".txt", ExtensionMime.TEXT.getMime());
		mimes.put(".inf", ExtensionMime.TEXT.getMime());
		mimes.put(".nfo", ExtensionMime.TEXT.getMime());
		
		mimes.put(".xml", ExtensionMime.XML.getMime());
		mimes.put(".dtd", ExtensionMime.XML.getMime());
		
		mimes.put(".mpeg", ExtensionMime.MPEG.getMime());
		mimes.put(".mpg", ExtensionMime.MPEG.getMime());
		mimes.put(".mpe", ExtensionMime.MPEG.getMime());
		
		mimes.put(".avi", ExtensionMime.AVI.getMime());
	}
	
	public void loadDefaultSsi() {
		ssis.add(".shtml");
		ssis.add(".shtm");
		ssis.add(".stm");
		ssis.add(".sht");
	}
	
	public static enum ExtensionMime {
		
		PS("application/postscript"), //
		RTF("application/rtf"), //
		AUDIO("audio/basic"), //
		OCTETSTREAM("application/octet-stream"), //
		DOC("application/msword"), //
		PDF("application/pdf"), //
		PPT("application/powerpoint"), //
		SMI("application/smil"), //
		JS("application/x-javascript"), //
		ZIP("application/zip"), //
		MIDI("audio/midi"), //
		MP3("audio/mpeg"), //
		WAV("audio/x-wav"), //
		GIF("image/gif"), //
		IEF("image/ief"), //
		JPEG("image/jpeg"), //
		PNG("image/png"), //
		TIFF("image/tiff"), //
		VRML("model/vrml"), //
		CSS("text/css"), //
		HTML("text/html"), //
		TEXT("text/plain"), //
		XML("text/xml"), //
		MPEG("video/mpeg"), //
		AVI("video/x-msvideo"); //
		
		private final String mime;
		
		private ExtensionMime(String mime) {
			this.mime = mime;
		}
		
		public String getMime() {
			return mime;
		}
		
	}
	
}