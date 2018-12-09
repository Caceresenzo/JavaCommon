package caceresenzo.libs.reversing.cloudflare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import caceresenzo.libs.http.client.webb.Webb;
import caceresenzo.libs.scripts.javascript.AbstractJavaScriptExecutor;
import caceresenzo.libs.string.StringUtils;

/**
 * Inspired by https://github.com/zhkrb/cloudflare-scrape-Android
 * 
 * @author Enzo CACERES
 * @author zhkrb
 */
public class CloudflareBypass implements Serializable {
	
	/* Constants */
	private static final int MAX_COUNT = 3;
	private static final int CONNECTION_TIMEOUT = 60000;
	private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;";
	
	/* Variables */
	private String url, userAgent;
	private Callback callback;
	private int retryCount;
	private URL connectionUrl;
	private List<HttpCookie> cookies;
	private CookieManager cookieManager;
	
	private HttpURLConnection checkHttpConnection, mainHttpConnection, redirectionHttpConnection;
	
	private boolean canVisit = false;
	
	/* Constructor */
	public CloudflareBypass(String url, Callback callback) {
		this(url, Webb.DEFAULT_USER_AGENT, callback);
	}
	
	/* Constructor */
	public CloudflareBypass(String url, String userAgent, Callback callback) {
		this.url = url;
		this.userAgent = userAgent;
		this.callback = callback;
	}
	
	public void extract() {
		if (callback == null) {
			return;
		}
		
		cookieManager = new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		
		CookieHandler.setDefault(cookieManager);
		HttpURLConnection.setFollowRedirects(false);
		
		while (!canVisit) {
			if (retryCount > MAX_COUNT) {
				break;
			}
			
			try {
				int responseCode = checkUrl();
				
				if (responseCode == 200) {
					canVisit = true;
					break;
				} else {
					getVisiteCookie();
				}
			} catch (Exception exception) {
				if (cookies != null) {
					cookies.clear();
				}
				
				callback.onException(exception);
			} finally {
				closeAllConnection();
			}
			
			retryCount++;
		}
		
		if (canVisit) {
			callback.onSuccess(cookies);
		} else {
			notifyError("Failed to get cookies.");
			callback.onFail();
		}
		
		closeAllConnection();
	}
	
	private void getVisiteCookie() throws IOException, InterruptedException {
		connectionUrl = new URL(url);
		
		mainHttpConnection = (HttpURLConnection) connectionUrl.openConnection();
		mainHttpConnection.setRequestMethod("GET");
		mainHttpConnection.setConnectTimeout(CONNECTION_TIMEOUT);
		mainHttpConnection.setReadTimeout(CONNECTION_TIMEOUT);
		
		if (StringUtils.validate(userAgent)) {
			mainHttpConnection.setRequestProperty("user-agent", userAgent);
		}
		
		mainHttpConnection.setRequestProperty("accept", ACCEPT);
		mainHttpConnection.setRequestProperty("referer", url);
		
		if (cookies != null && cookies.size() > 0) {
			mainHttpConnection.setRequestProperty("cookie", listToString(cookies));
		}
		
		mainHttpConnection.setUseCaches(false);
		mainHttpConnection.connect();
		
		switch (mainHttpConnection.getResponseCode()) {
			case HttpURLConnection.HTTP_OK: {
				e("MainUrl", "visit website success");
				return;
			}
			
			case HttpURLConnection.HTTP_FORBIDDEN: {
				e("MainUrl", "IP block or cookie err");
				return;
			}
			
			case HttpURLConnection.HTTP_UNAVAILABLE: {
				InputStream mInputStream = checkHttpConnection.getErrorStream();
				BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
				StringBuilder sb = new StringBuilder();
				String str;
				while ((str = mBufferedReader.readLine()) != null) {
					sb.append(str);
				}
				mInputStream.close();
				mBufferedReader.close();
				cookies = cookieManager.getCookieStore().getCookies();
				str = sb.toString();
				getCheckAnswer(str);
				break;
			}
			
			default: {
				break;
			}
		}
	}
	
	/**
	 * ????????cookies
	 * 
	 * @param str
	 */
	private void getCheckAnswer(String str) throws InterruptedException, IOException {
		String jschl_vc = regex(str, "name=\"jschl_vc\" value=\"(.+?)\"").get(0); // ????
		String pass = regex(str, "name=\"pass\" value=\"(.+?)\"").get(0); //
		double jsChallengeAnswer = getAnswer(str);
		notifyError(String.valueOf(jsChallengeAnswer));
		Thread.sleep(3000);
		String request = String.valueOf("https://" + connectionUrl.getHost()) + "/cdn-cgi/l/chk_jschl?" + "jschl_vc=" + jschl_vc + "&pass=" + pass + "&jschl_answer=" + jsChallengeAnswer;
		e("RedirectUrl", request);
		getRedirectResponse(request);
	}
	
	private void getRedirectResponse(String request) throws IOException {
		HttpURLConnection.setFollowRedirects(false);
		redirectionHttpConnection = (HttpURLConnection) new URL(request).openConnection();
		redirectionHttpConnection.setRequestMethod("GET");
		redirectionHttpConnection.setConnectTimeout(CONNECTION_TIMEOUT);
		redirectionHttpConnection.setReadTimeout(CONNECTION_TIMEOUT);
		
		if (StringUtils.validate(userAgent)) {
			redirectionHttpConnection.setRequestProperty("user-agent", userAgent);
		}
		
		redirectionHttpConnection.setRequestProperty("accept", ACCEPT);
		redirectionHttpConnection.setRequestProperty("referer", request);
		
		if (cookies != null && cookies.size() > 0) {
			redirectionHttpConnection.setRequestProperty("cookie", listToString(cookies));
		}
		
		redirectionHttpConnection.setUseCaches(false);
		redirectionHttpConnection.connect();
		
		switch (redirectionHttpConnection.getResponseCode()) {
			case HttpURLConnection.HTTP_MOVED_TEMP:
			case HttpURLConnection.HTTP_OK: {
				cookies = cookieManager.getCookieStore().getCookies();
				break;
			}
			
			default: {
				throw new IOException("Not handled response code: " + redirectionHttpConnection.getResponseCode());
			}
		}
	}
	
	private int checkUrl() throws IOException {
		URL connectionUrl = new URL(url);
		
		checkHttpConnection = (HttpURLConnection) connectionUrl.openConnection();
		checkHttpConnection.setRequestMethod("GET");
		checkHttpConnection.setConnectTimeout(CONNECTION_TIMEOUT);
		checkHttpConnection.setReadTimeout(CONNECTION_TIMEOUT);
		
		if (StringUtils.validate(userAgent)) {
			checkHttpConnection.setRequestProperty("user-agent", userAgent);
		}
		
		checkHttpConnection.setRequestProperty("accept", ACCEPT);
		checkHttpConnection.setRequestProperty("referer", url);
		
		if (cookies != null && cookies.size() > 0) {
			checkHttpConnection.setRequestProperty("cookie", listToString(cookies));
		}
		
		checkHttpConnection.setUseCaches(false);
		checkHttpConnection.connect();
		
		return checkHttpConnection.getResponseCode();
	}
	
	private void closeAllConnection() {
		if (checkHttpConnection != null) {
			checkHttpConnection.disconnect();
		}
		if (mainHttpConnection != null) {
			mainHttpConnection.disconnect();
		}
		if (redirectionHttpConnection != null) {
			redirectionHttpConnection.disconnect();
		}
	}
	
	private double getAnswer(String string) {
		double a = 0;
		
		try {
			List<String> s = regex(string, "var s,t,o,p,b,r,e,a,k,i,n,g,f, " + "(.+?)=\\{\"(.+?)\"");
			String varA = s.get(0);
			String varB = s.get(1);
			
			StringBuilder sb = new StringBuilder();
			
			sb.append("var a=");
			sb.append(regex(string, varA + "=\\{\"" + varB + "\":(.+?)\\}").get(0));
			sb.append(";");
			
			List<String> b = regex(string, varA + "\\." + varB + "(.+?)\\;");
			
			for (int i = 0; i < b.size() - 1; i++) {
				sb.append("a");
				sb.append(b.get(i));
				sb.append(";");
			}
			
			sb.append("a=a.toString();");
			
			AbstractJavaScriptExecutor executor = AbstractJavaScriptExecutor.get();
			
			a = Double.valueOf(String.valueOf(executor.eval(sb.toString())));
			
			List<String> fixNum = regex(string, "toFixed\\((.+?)\\)");
			if (fixNum != null) {
				a = Double.parseDouble(String.valueOf(executor.eval("String(" + String.valueOf(a) + ".toFixed(" + fixNum.get(0) + "));")));
			}
			
			a += new URL(url).getHost().length();
		} catch (Exception exception) {
			callback.onException(exception);
		}
		
		return a;
	}
	
	private List<String> regex(String text, String regex) {
		try {
			Matcher matcher = Pattern.compile(regex).matcher(text);
			List<String> group = new ArrayList<>();
			
			while (matcher.find()) {
				if (matcher.groupCount() >= 1) {
					if (matcher.groupCount() > 1) {
						group.add(matcher.group(1));
						group.add(matcher.group(2));
					} else {
						group.add(matcher.group(1));
					}
				}
			}
			return group;
		} catch (NullPointerException exception) {
			;
		}
		
		return null;
	}
	
	public static String listToString(List list) {
		char separator = ";".charAt(0);
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(separator);
		}
		
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
	
	public static Map<String, String> List2Map(List<HttpCookie> list) {
		Map<String, String> map = new HashMap<>();
		
		try {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					String[] listStr = list.get(i).toString().split("=");
					map.put(listStr[0], listStr[1]);
				}
			} else {
				return map;
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	private void e(String tag, String content) {
		// Logger.error(tag + " %s", content);
		// System.out.println(tag + ": " + content);
	}
	
	private void notifyError(String content) {
		// Logger.error("cloudflare %s", content);
		// System.out.println(content);
	}
	
	public interface Callback extends Serializable {
		void onSuccess(List<HttpCookie> cookieList);
		
		void onFail();
		
		void onException(Exception exception);
	}
	
}