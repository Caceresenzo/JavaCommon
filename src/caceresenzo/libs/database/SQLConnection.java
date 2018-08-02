package caceresenzo.libs.database;

public class SQLConnection {
	
	protected String host;
	protected int port;
	protected String database;
	protected String user;
	protected String password;
	
	public SQLConnection(String host, Integer port, String database, String user, String password) {
		this.host = host;
		this.port = (port == null || port == 0 || port == -1) ? 3306 : port;
		this.database = database;
		this.user = user;
		this.password = password;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public String getDatabase() {
		return this.database;
	}
	
	public String getUser() {
		return this.user;
	}
	
	public String getPassword() {
		return this.password;
	}
	
}