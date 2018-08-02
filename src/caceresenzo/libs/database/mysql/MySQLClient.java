package caceresenzo.libs.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import caceresenzo.libs.database.SQLConnection;
import caceresenzo.libs.logger.Logger;

public class MySQLClient {
	
	private boolean connected;
	private boolean error;
	private String lastError;
	
	protected String host;
	protected int port;
	protected String database;
	protected String user;
	protected String password;
	
	protected SQLConnection sqlconnection;
	
	protected Connection connection;
	protected Statement statement;
	
	public MySQLClient(SQLConnection sqlconnection) {
		this.connected = false;
		this.error = false;
		
		this.host = sqlconnection.getHost();
		this.port = sqlconnection.getPort();
		this.database = sqlconnection.getDatabase();
		this.user = sqlconnection.getUser();
		this.password = sqlconnection.getPassword();
		
		this.sqlconnection = sqlconnection;
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
			this.statement = this.connection.createStatement();
			this.connected = true;
			
			Logger.debug("Connected to database: " + this.host + ":" + this.port + "/" + this.database + " - " + this.user + "@" + "<a certain password>");
		} catch(ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException exception) {
			Logger.exception(exception);
			this.error = true;
			this.lastError = exception.getMessage();
		}
	}
	
	public ResultSet executeQuery(String query) {
		ResultSet resultset = null;
		try {
			resultset = this.statement.executeQuery(query);
		} catch(SQLException sqlException) {
			Logger.exception(sqlException);
			this.error = true;
			this.lastError = sqlException.getMessage();
		}
		return resultset;
	}
	
	public boolean executeSQL(String sql) {
		try {
			this.statement.execute(sql);
			return true;
		} catch(SQLException sqlException) {
			Logger.exception(sqlException);
			this.error = true;
			this.lastError = sqlException.getMessage();
			return false;
		}
	}
	
	public SQLConnection getConnection() {
		return this.sqlconnection;
	}
	
	public boolean isConnected() {
		return this.connected;
	}
	
	public boolean isError() {
		return this.error;
	}
	
	public void setErrorState(boolean state) {
		this.error = state;
	}
	
	public String getLastError() {
		return this.lastError;
	}
	
}