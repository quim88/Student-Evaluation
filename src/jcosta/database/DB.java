package jcosta.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * This class simple sets up the connection and returns the connection instance.
 * 
 * @author Joaquim Costa
 * 
 */
public class DB
{
	private static Connection _conn;

	/**
	 * this method setup the connection with the database
	 * 
	 * @return
	 */
	public static Connection getConnection()
	{
		try
		{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

			_conn = DriverManager.getConnection("jdbc:derby:cs455_jcosta");
		} catch(Exception e)
		{
			_conn = null;
			// e.printStackTrace();
			System.out.println("Error: " + e.getMessage());
		}

		return _conn;
	}

}
