package uno;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Database
{
	private Connection conn;
	
	public Database()
	{
		// Read from the properties
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream("uno/db.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String user = prop.getProperty("user");
		String pass = prop.getProperty("password");
		String url = prop.getProperty("url");
		
		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Basic getter for the connection object
	public Connection getConnection()
	{
		return conn;
	}

	private ArrayList<String> searchUserInfo(String query)
	{	
		query = "SELECT username, aes_decrypt(password, 'key') FROM user;";
		
		ArrayList<String> result = new ArrayList<String>();
		try {
		Statement statement = conn.createStatement();


		ResultSet rs = statement.executeQuery(query);

		if (!rs.next()) {
			return null;
		}
		else {
			ResultSetMetaData rmd = rs.getMetaData();
			int noColumns = rmd.getColumnCount();

			int i = 1;

			do {
				String record = " ";
				for(i = 1; i <= noColumns; i++) {
					record += rs.getString(i);
					if (i < noColumns)
						record += ",";
				}
				result.add(record);
			}while (rs.next());
		}
		}catch(SQLException e) {
			return null;
		}
		return result;
	}

	public boolean validateLogin(String username, String password) {
		searchUserInfo(username);
		return true;
	}
	
	private ArrayList<String> searchUsername(String query)
	{
		query = "SELECT username FROM user;";
		
		ArrayList<String> result = new ArrayList<String>();
		try {
		Statement statement = conn.createStatement();


		ResultSet rs = statement.executeQuery(query);

		if (!rs.next()) {
			return null;
		}
		else {
			ResultSetMetaData rmd = rs.getMetaData();
			int noColumns = rmd.getColumnCount();

			int i = 1;

			do {
				String record = " ";
				for(i = 1; i <= noColumns; i++) {
					record += rs.getString(i);
					if (i < noColumns)
						record += ",";
				}
				result.add(record);
			}while (rs.next());
		}
		}catch(SQLException e) {
			return null;
		}
		return result;
	}
	
	public boolean usernameIsUnique(String username) {
		if (searchUsername(username).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void executeDML(String dml) throws SQLException
	{
		Statement statement = conn.createStatement();
		
		statement.execute(dml);
	}

}

