package uno;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Database
{
	private Connection conn;
	
	public Database(String file)
	{
		Properties prop = new Properties();

		try {
			FileInputStream fis = new FileInputStream(file);
			try {
				prop.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String user = prop.getProperty("user");
		String pass = prop.getProperty("password");
		String url = prop.getProperty("url");

		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void executeDML(String dml) throws SQLException
	{
		Statement statement = conn.createStatement();
		
		statement.execute(dml);
	}

}

