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
		try 
		{
			FileInputStream fis = new FileInputStream("uno/db.properties");
			prop.load(fis);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

		String user = prop.getProperty("user");
		String pass = prop.getProperty("password");
		String url = prop.getProperty("url");

		try 
		{
			conn = DriverManager.getConnection(url, user, pass);
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	// Basic getter for the connection object
	public Connection getConnection()
	{
		return conn;
	}

	// Run a query to the database
	private ArrayList<String> executeQuery(String query)
	{
		ArrayList<String> result = new ArrayList<String>();
		try 
		{
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(query);
			if (!rs.next())
			{
				return null;
			}
			else 
			{
				ResultSetMetaData rmd = rs.getMetaData();
				int noCols = rmd.getColumnCount();
				int i = 1;
				do 
				{
					String record = "";
					for (i=1; i<=noCols; i++) 
					{
						record += rs.getString(i);
						record += ",";
						result.add(record);
					}
				} 
				while(rs.next());
			}
		} 
		catch (SQLException e)
		{
			return null;
		}
		return result;
	}

	// Method to determine whether or not user/pass combination is accurate
	public boolean validateLogin(String username, String password) {
		// Run a simple query to verify that an account exists with the proper username and password
		// If so, a simple 1 will be returned. If not, it should be empty
		// Remember to decrypt the data with aes_decrypt and 'key' as key
		String query = "SELECT 1 FROM users WHERE username = '" + username + "' && aes_decrypt(password, 'key') = '" + password + "';";

		// All we need to know is if the account is valid
		if (executeQuery(query) == null)
		{
			return false;
		}
		else 
		{
			return true;
		}
	}

	// Method for creating a new account.
	public boolean createNewAccount(String username, String password)
	{
		// Ensure the username doesn't already exist
		if (!usernameIsUnique(username)) 
		{
			return false;
		}

		// Save the username and password by running executeUpdate
		// Ensure the password being inserted is encrypted with aes_encrypt and 'key' as key
		try
		{
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO users (username, password) "
					+ "VALUES ('" + username + "', aes_encrypt('" + password + "', 'key'));");
		} 
		catch (SQLException e) 
		{
			return false;
		}
		return true;
	}

	// Method to view if username has been taken
	public boolean usernameIsUnique(String username)
	{
		if (executeQuery("SELECT 1 FROM users WHERE username = '" + username + "';") != null) 
		{
			return false;
		} 
		else 
		{
			return true;
		}
	}

	// Method GameServer calls once a user has won a game
	// Database keeps tally of wins per user, this method ups the count by one
	public boolean increaseWinCount(String username) 
	{
		ArrayList<String> currentWinResults = executeQuery("SELECT wins FROM wins WHERE (username='"+username+"');");
		String query = "";
		
		// See if user has won before
		if (currentWinResults != null) 
		{
			// Parse the int (be sure to strip the trailing comma
			int currentWins = Integer.parseInt(currentWinResults.get(0).replaceAll(",$", ""));
			query = "UPDATE wins SET wins=" + ((Integer)(currentWins+1)).toString() + " WHERE username='"+username+"';";
		} 
		else 
		{
			query = "INSERT INTO wins (username, wins) VALUES ('"+username+"', 1);";
		}
		
		// This will fail if user is not in users table, but that's what we want
		try
		{
			Statement st = conn.createStatement();
			st.executeUpdate(query);
		} 
		catch (SQLException e)
		{
			return false;
		}
		
		return true;
	}
}
