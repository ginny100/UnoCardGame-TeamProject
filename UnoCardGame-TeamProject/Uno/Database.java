package Uno;

import java.sql.*;
import java.util.*;
import java.io.*;

public class Database
{
	private Connection conn;
	//Add any other data fields you like – at least a Connection object is mandatory
	public Database()
	{
		Properties prop = new Properties();

		try {
			FileInputStream fis = new FileInputStream("Uno/db.properties");
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

	public ArrayList<String> UserPassLook(String query)
	{	
		query = "Select username, aes_decrypt(password, 'key') From User;";
		
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
	
	public boolean query1(String query)
	{
		ArrayList<String> result = new ArrayList<String>();
		result = UserPassLook(query);
		String tokens[] = query.split(" ");
		if (result != null)
		{
			for (String row : result)
			{
				String tokens1[] = row.split(",");
				if(tokens1[0].equals(" " + tokens[0]) && tokens1[1].equals(tokens[1])) {
					return true;
				}
			}
		}
		else
		{
			System.out.println("Error executing query.");
		}
		return false;
	}
	
	public ArrayList<String> UserLook(String query)
	{
		query = "Select username From User;";
		
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
	
	public boolean query2(String query)
	{
		ArrayList<String> result = new ArrayList<String>();
		result = UserLook(query);
		if (result != null)
		{
			for (String row : result)
			{
				if(row.equals(" " + query)) {
					return true;
				}
			}
		}
		else
		{
			System.out.println("Error executing query.");
		}
		return false;
	}

	public void executeDML(String dml) throws SQLException
	{
		Statement statement = conn.createStatement();
		
		statement.execute(dml);
	}

}

