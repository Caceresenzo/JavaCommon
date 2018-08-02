package caceresenzo.libs.url;

import java.net.URLDecoder;
import java.util.HashMap;

public class UrlUtils {
	
	public static HashMap<String, String> parseParameters(String compact, boolean decodeValue) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		String[] urlSplit = parseUrl(compact);
		if (urlSplit == null || urlSplit.length < 2) {
			return parameters;
		}
		
		for (String paramAndArg : String.valueOf(urlSplit[1]).split("&")) {
			String[] split = paramAndArg.split("=", 2);
			
			try {
				String key = split[0];
				String value = decodeValue ? URLDecoder.decode(split[1], "UTF-8") : split[1];
				parameters.put(key, value);
			} catch (Exception exception) {
				parameters.put(paramAndArg, null);
			}
		}
		
		return parameters;
	}
	
	public static String parseRessource(String compact) {
		String[] split = parseUrl(compact);
		
		if (split != null && split.length > 0) {
			return split[0];
		}
		
		return null;
	}
	
	/**
	 * array[0] = path/to.file //
	 * 
	 * array[1] = param=eters&...
	 */
	public static String[] parseUrl(String compact) {
		return String.valueOf(compact).split("\\?");
	}
	
}