package caceresenzo.libs.http.client.webb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import caceresenzo.libs.http.TrustAnyTrustManager;
import caceresenzo.libs.json.JsonArray;
import caceresenzo.libs.json.JsonObject;

/**
 * Lightweight Java HTTP-Client for calling Json REST-Services (especially for Android).
 *
 * @author hgoebl
 */
public class Webb {
	public static final String DEFAULT_USER_AGENT = WebbConstante.DEFAULT_USER_AGENT;
	public static final String APP_FORM = WebbConstante.APPLICATION_FORM;
	public static final String APP_Json = WebbConstante.APPLICATION_JSON;
	public static final String APP_BINARY = WebbConstante.APPLICATION_BINARY;
	public static final String TEXT_PLAIN = WebbConstante.TEXT_PLAIN;
	public static final String HDR_CONTENT_TYPE = WebbConstante.HDR_CONTENT_TYPE;
	public static final String HDR_CONTENT_ENCODING = WebbConstante.HDR_CONTENT_ENCODING;
	public static final String HDR_ACCEPT = WebbConstante.HDR_ACCEPT;
	public static final String HDR_ACCEPT_ENCODING = WebbConstante.HDR_ACCEPT_ENCODING;
	public static final String HDR_USER_AGENT = WebbConstante.HDR_USER_AGENT;
	public static final String HDR_AUTHORIZATION = "Authorization";
	
	static final Map<String, Object> globalHeaders = new LinkedHashMap<String, Object>();
	static String globalBaseUri;
	
	static Integer connectTimeout = 10000; // 10 seconds
	static Integer readTimeout = 3 * 60000; // 5 minutes
	static int jsonIndentFactor = -1;
	
	Boolean followRedirects;
	String baseUri;
	Map<String, Object> defaultHeaders;
	SSLSocketFactory sslSocketFactory;
	HostnameVerifier hostnameVerifier;
	RetryManager retryManager;
	
	private boolean disableSslVerification;
	
	protected Webb(boolean disableSslVerification) {
		this.disableSslVerification = disableSslVerification;
	}
	
	/**
	 * Create an instance which can be reused for multiple requests in the same Thread.
	 * 
	 * @return the created instance.
	 */
	public static Webb create(boolean disableSslVerification) {
		return new Webb(disableSslVerification);
	}
	
	/**
	 * Create an instance which can be reused for multiple requests in the same Thread.
	 * 
	 * @return the created instance.
	 */
	public static Webb create() {
		return create(false);
	}
	
	/**
	 * Set the value for a named header which is valid for all requests in the running JVM. <br>
	 * The value can be overwritten by calling {@link Webb#setDefaultHeader(String, Object)} and/or {@link caceresenzo.libs.http.client.webb.Request#header(String, Object)}. <br>
	 * For the supported types for values see {@link Request#header(String, Object)}.
	 *
	 * @param name
	 *            name of the header (regarding HTTP it is not case-sensitive, but here case is important).
	 * @param value
	 *            value of the header. If <code>null</code> the header value is cleared (effectively not set).
	 *
	 * @see #setDefaultHeader(String, Object)
	 * @see caceresenzo.libs.http.client.webb.Request#header(String, Object)
	 */
	public static void setGlobalHeader(String name, Object value) {
		if (value != null) {
			globalHeaders.put(name, value);
		} else {
			globalHeaders.remove(name);
		}
	}
	
	/**
	 * Set the base URI for all requests starting in this JVM from now. <br>
	 * For all requests this value is taken as a kind of prefix for the effective URI, so you can address the URIs relatively. The value is only taken when {@link Webb#setBaseUri(String)} is not called or called with <code>null</code>.
	 *
	 * @param globalBaseUri
	 *            the prefix for all URIs of new Requests.
	 * @see #setBaseUri(String)
	 */
	public static void setGlobalBaseUri(String globalBaseUri) {
		Webb.globalBaseUri = globalBaseUri;
	}
	
	/**
	 * The number of characters to indent child properties, <code>-1</code> for "productive" code. <br>
	 * Default is production ready Json (-1) means no indentation (single-line serialization).
	 * 
	 * @param indentFactor
	 *            the number of spaces to indent
	 */
	public static void setJsonIndentFactor(int indentFactor) {
		Webb.jsonIndentFactor = indentFactor;
	}
	
	/**
	 * Set the timeout in milliseconds for connecting the server. <br>
	 * In contrast to {@link java.net.HttpURLConnection}, we use a default timeout of 10 seconds, since no timeout is odd.<br>
	 * Can be overwritten for each Request with {@link caceresenzo.libs.http.client.webb.Request#connectTimeout(int)}.
	 * 
	 * @param globalConnectTimeout
	 *            the new timeout or <code>&lt;= 0</code> to use HttpURLConnection default timeout.
	 */
	public static void setConnectTimeout(int globalConnectTimeout) {
		connectTimeout = globalConnectTimeout > 0 ? globalConnectTimeout : null;
	}
	
	/**
	 * Set the timeout in milliseconds for getting response from the server. <br>
	 * In contrast to {@link java.net.HttpURLConnection}, we use a default timeout of 3 minutes, since no timeout is odd.<br>
	 * Can be overwritten for each Request with {@link caceresenzo.libs.http.client.webb.Request#readTimeout(int)}.
	 * 
	 * @param globalReadTimeout
	 *            the new timeout or <code>&lt;= 0</code> to use HttpURLConnection default timeout.
	 */
	public static void setReadTimeout(int globalReadTimeout) {
		readTimeout = globalReadTimeout > 0 ? globalReadTimeout : null;
	}
	
	/**
	 * See <a href="http://docs.oracle.com/javase/7/docs/api/java/net/HttpURLConnection.html#setInstanceFollowRedirects(boolean)"> </a>. <br>
	 * Use this method to set the behaviour for all requests created by this instance when receiving redirect responses. You can overwrite the setting for a single request by calling {@link Request#followRedirects(boolean)}.
	 * 
	 * @param auto
	 *            <code>true</code> to automatically follow redirects (HTTP status code 3xx). Default value comes from HttpURLConnection and should be <code>true</code>.
	 */
	public void setFollowRedirects(boolean auto) {
		this.followRedirects = auto;
	}
	
	/**
	 * Set a custom {@link javax.net.ssl.SSLSocketFactory}, most likely to relax Certification checking.
	 * 
	 * @param sslSocketFactory
	 *            the factory to use (see test cases for an example).
	 */
	public void setSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
		this.sslSocketFactory = sslSocketFactory;
	}
	
	/**
	 * Set a custom {@link javax.net.ssl.HostnameVerifier}, most likely to relax host-name checking.
	 * 
	 * @param hostnameVerifier
	 *            the verifier (see test cases for an example).
	 */
	public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
		this.hostnameVerifier = hostnameVerifier;
	}
	
	/**
	 * Set the base URI for all requests created from this instance. <br>
	 * For all requests this value is taken as a kind of prefix for the effective URI, so you can address the URIs relatively. The value takes precedence over the value set in {@link #setGlobalBaseUri(String)}.
	 *
	 * @param baseUri
	 *            the prefix for all URIs of new Requests.
	 * @see #setGlobalBaseUri(String)
	 */
	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}
	
	/**
	 * Returns the base URI of this instance.
	 *
	 * @return base URI
	 */
	public String getBaseUri() {
		return baseUri;
	}
	
	/**
	 * Set the value for a named header which is valid for all requests created by this instance. <br>
	 * The value takes precedence over {@link Webb#setGlobalHeader(String, Object)} but can be overwritten by {@link caceresenzo.libs.http.client.webb.Request#header(String, Object)}. <br>
	 * For the supported types for values see {@link Request#header(String, Object)}.
	 *
	 * @param name
	 *            name of the header (regarding HTTP it is not case-sensitive, but here case is important).
	 * @param value
	 *            value of the header. If <code>null</code> the header value is cleared (effectively not set). When setting the value to null, a value from global headers can shine through.
	 *
	 * @see #setGlobalHeader(String, Object)
	 * @see caceresenzo.libs.http.client.webb.Request#header(String, Object)
	 */
	public void setDefaultHeader(String name, Object value) {
		if (defaultHeaders == null) {
			defaultHeaders = new HashMap<String, Object>();
		}
		if (value == null) {
			defaultHeaders.remove(name);
		} else {
			defaultHeaders.put(name, value);
		}
	}
	
	/**
	 * Registers an alternative {@link caceresenzo.libs.http.client.webb.RetryManager}.
	 * 
	 * @param retryManager
	 *            the new manager for deciding whether it makes sense to retry a request.
	 */
	public void setRetryManager(RetryManager retryManager) {
		this.retryManager = retryManager;
	}
	
	/**
	 * Creates a <b>GET HTTP</b> request with the specified absolute or relative URI.
	 * 
	 * @param pathOrUri
	 *            the URI (will be concatenated with global URI or default URI without further checking). If it starts already with http:// or https:// this URI is taken and all base URIs are ignored.
	 * @return the created Request object (in fact it's more a builder than a real request object)
	 */
	public Request get(String pathOrUri) {
		return new Request(this, Request.Method.GET, buildPath(pathOrUri));
	}
	
	/**
	 * Creates a <b>POST</b> HTTP request with the specified absolute or relative URI.
	 * 
	 * @param pathOrUri
	 *            the URI (will be concatenated with global URI or default URI without further checking) If it starts already with http:// or https:// this URI is taken and all base URIs are ignored.
	 * @return the created Request object (in fact it's more a builder than a real request object)
	 */
	public Request post(String pathOrUri) {
		return new Request(this, Request.Method.POST, buildPath(pathOrUri));
	}
	
	/**
	 * Creates a <b>PUT</b> HTTP request with the specified absolute or relative URI.
	 * 
	 * @param pathOrUri
	 *            the URI (will be concatenated with global URI or default URI without further checking) If it starts already with http:// or https:// this URI is taken and all base URIs are ignored.
	 * @return the created Request object (in fact it's more a builder than a real request object)
	 */
	public Request put(String pathOrUri) {
		return new Request(this, Request.Method.PUT, buildPath(pathOrUri));
	}
	
	/**
	 * Creates a <b>DELETE</b> HTTP request with the specified absolute or relative URI.
	 * 
	 * @param pathOrUri
	 *            the URI (will be concatenated with global URI or default URI without further checking) If it starts already with http:// or https:// this URI is taken and all base URIs are ignored.
	 * @return the created Request object (in fact it's more a builder than a real request object)
	 */
	public Request delete(String pathOrUri) {
		return new Request(this, Request.Method.DELETE, buildPath(pathOrUri));
	}
	
	private String buildPath(String pathOrUri) {
		if (pathOrUri == null) {
			throw new IllegalArgumentException("pathOrUri must not be null");
		}
		if (pathOrUri.startsWith("http://") || pathOrUri.startsWith("https://")) {
			return pathOrUri;
		}
		String myBaseUri = baseUri != null ? baseUri : globalBaseUri;
		return myBaseUri == null ? pathOrUri : myBaseUri + pathOrUri;
	}
	
	<T> Response<T> execute(Request request, Class<T> clazz) {
		Response<T> response = null;
		
		if (request.getRetryCount() == 0) {
			// no retry -> just delegate to inner method
			response = _execute(request, clazz);
		} else {
			if (retryManager == null) {
				retryManager = RetryManager.DEFAULT;
			}
			for (int tries = 0; tries <= request.getRetryCount(); ++tries) {
				try {
					response = _execute(request, clazz);
					if (tries >= request.getRetryCount() || !retryManager.isRetryUseful(response)) {
						break;
					}
				} catch (WebbException we) {
					// analyze: is exception recoverable?
					if (tries >= request.getRetryCount() || !retryManager.isRecoverable(we)) {
						throw we;
					}
				}
				if (request.isWaitExponential()) {
					retryManager.wait(tries);
				}
			}
		}
		if (response == null) {
			throw new IllegalStateException(); // should never reach this line
		}
		if (request.isEnsureSuccess()) {
			response.ensureSuccess();
		}
		
		return response;
	}
	
	private <T> Response<T> _execute(Request request, Class<T> clazz) {
		Response<T> response = new Response<T>(request);
		
		InputStream is = null;
		boolean closeStream = true;
		HttpURLConnection connection = null;
		
		try {
			String uri = request.getUri();
			if (request.getMethod() == Request.Method.GET && !uri.contains("?") && request.getParams() != null && !request.getParams().isEmpty()) {
				uri += "?" + WebbUtils.queryString(request.getParams());
			}
			URL apiUrl = new URL(uri);
			connection = (HttpURLConnection) apiUrl.openConnection();
			
			if (disableSslVerification && connection instanceof HttpsURLConnection) {
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { new TrustAnyTrustManager() }, new java.security.SecureRandom());
				
				((HttpsURLConnection) connection).setSSLSocketFactory(sc.getSocketFactory());
				
				((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
			}
			
			prepareSslConnection(connection);
			connection.setRequestMethod(request.getMethod().name());
			if (request.getFollowRedirects() != null) {
				connection.setInstanceFollowRedirects(request.getFollowRedirects());
			}
			connection.setUseCaches(request.isUseCaches());
			setTimeouts(request, connection);
			if (request.getIfModifiedSince() != null) {
				connection.setIfModifiedSince(request.getIfModifiedSince());
			}
			
			WebbUtils.addRequestProperties(connection, mergeHeaders(request.getHeaders()));
			if (clazz == JsonObject.class || clazz == JsonArray.class) {
				WebbUtils.ensureRequestProperty(connection, HDR_ACCEPT, APP_Json);
			}
			
			if (request.getMethod() != Request.Method.GET && request.getMethod() != Request.Method.DELETE) {
				if (request.isStreamPayload()) {
					WebbUtils.setContentTypeAndLengthForStreaming(connection, request, request.isCompress());
					connection.setDoOutput(true);
					streamBody(connection, request.getPayload(), request.isCompress());
				} else {
					byte[] requestBody = WebbUtils.getPayloadAsBytesAndSetContentType(connection, request, request.isCompress(), jsonIndentFactor);
					
					if (requestBody != null) {
						connection.setDoOutput(true);
						writeBody(connection, requestBody);
					}
				}
			} else {
				connection.connect();
			}
			
			response.setConnection(connection);
			response.setStatusCode(connection.getResponseCode());
			response.setResponseMessage(connection.getResponseMessage());
			
			// get the response body (if any)
			is = response.isSuccess() ? connection.getInputStream() : connection.getErrorStream();
			is = WebbUtils.wrapStream(connection.getContentEncoding(), is);
			
			if (clazz == InputStream.class) {
				is = new AutoDisconnectInputStream(connection, is);
			}
			if (response.isSuccess()) {
				WebbUtils.parseResponseBody(clazz, response, is);
			} else {
				WebbUtils.parseErrorResponse(clazz, response, is);
			}
			if (clazz == InputStream.class) {
				closeStream = false;
			}
			
			return response;
			
		} catch (WebbException e) {
			
			throw e;
			
		} catch (Exception e) {
			
			throw new WebbException(e);
			
		} finally {
			if (closeStream) {
				if (is != null) {
					try {
						is.close();
					} catch (Exception ignored) {
					}
				}
				if (connection != null) {
					try {
						connection.disconnect();
					} catch (Exception ignored) {
					}
				}
			}
		}
	}
	
	private void setTimeouts(Request request, HttpURLConnection connection) {
		if (request.getConnectTimeout() != null || connectTimeout != null) {
			connection.setConnectTimeout(request.getConnectTimeout() != null ? request.getConnectTimeout() : connectTimeout);
		}
		if (request.getReadTimeout() != null || readTimeout != null) {
			connection.setReadTimeout(request.getReadTimeout() != null ? request.getReadTimeout() : readTimeout);
		}
	}
	
	private void writeBody(HttpURLConnection connection, byte[] body) throws IOException {
		// Android StrictMode might complain about not closing the connection:
		// "E/StrictMode﹕ A resource was acquired at attached stack trace but never released"
		// It seems like some kind of bug in special devices (e.g. 4.0.4/sony) but does not
		// happen e.g. on 4.4.2/Moto G.
		// Closing the stream in the try block might help sometimes (it's intermittently),
		// but I don't want to deal with the IOException which can be thrown in close().
		OutputStream os = null;
		try {
			os = connection.getOutputStream();
			os.write(body);
			os.flush();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception ignored) {
				}
			}
		}
	}
	
	private void streamBody(HttpURLConnection connection, Object body, boolean compress) throws IOException {
		InputStream is;
		boolean closeStream;
		
		if (body instanceof File) {
			is = new FileInputStream((File) body);
			closeStream = true;
		} else {
			is = (InputStream) body;
			closeStream = false;
		}
		
		// "E/StrictMode﹕ A resource was acquired at attached stack trace but never released"
		// see comments about this problem in #writeBody()
		OutputStream os = null;
		try {
			os = connection.getOutputStream();
			if (compress) {
				os = new GZIPOutputStream(os);
			}
			WebbUtils.copyStream(is, os);
			os.flush();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception ignored) {
				}
			}
			if (is != null && closeStream) {
				try {
					is.close();
				} catch (Exception ignored) {
				}
			}
		}
	}
	
	private void prepareSslConnection(HttpURLConnection connection) {
		if ((hostnameVerifier != null || sslSocketFactory != null) && connection instanceof HttpsURLConnection) {
			HttpsURLConnection sslConnection = (HttpsURLConnection) connection;
			if (hostnameVerifier != null) {
				sslConnection.setHostnameVerifier(hostnameVerifier);
			}
			if (sslSocketFactory != null) {
				sslConnection.setSSLSocketFactory(sslSocketFactory);
			}
		}
	}
	
	Map<String, Object> mergeHeaders(Map<String, Object> requestHeaders) {
		Map<String, Object> headers = null;
		if (!globalHeaders.isEmpty()) {
			headers = new LinkedHashMap<String, Object>();
			headers.putAll(globalHeaders);
		}
		if (defaultHeaders != null) {
			if (headers == null) {
				headers = new LinkedHashMap<String, Object>();
			}
			headers.putAll(defaultHeaders);
		}
		if (requestHeaders != null) {
			if (headers == null) {
				headers = requestHeaders;
			} else {
				headers.putAll(requestHeaders);
			}
		}
		return headers;
	}
	
	/**
	 * Disconnect the underlying <code>HttpURLConnection</code> on close.
	 */
	private static class AutoDisconnectInputStream extends FilterInputStream {
		
		/**
		 * The underlying <code>HttpURLConnection</code>.
		 */
		private final HttpURLConnection connection;
		
		/**
		 * Creates an <code>AutoDisconnectInputStream</code> by assigning the argument <code>in</code> to the field <code>this.in</code> so as to remember it for later use.
		 * 
		 * @param connection
		 *            the underlying connection to disconnect on close.
		 * @param in
		 *            the underlying input stream, or <code>null</code> if this instance is to be created without an underlying stream.
		 */
		protected AutoDisconnectInputStream(final HttpURLConnection connection, final InputStream in) {
			super(in);
			this.connection = connection;
		}
		
		@Override
		public void close() throws IOException {
			try {
				super.close();
			} finally {
				connection.disconnect();
			}
		}
	}
	
}