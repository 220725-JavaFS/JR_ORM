package com.revature.utils;

import java.sql.Connection; //java.sql is the JDBC package
import java.sql.DriverManager;
import java.sql.SQLException;

import com.revature.modelsORM.Connectivity;

public class ConnectionUtil {

	//Singleton design pattern here - only allows one instance of a Class to exist in memory at a time
	
	private static Connection connection;
	
	public static Connection getConnection(Connectivity con) throws SQLException {
		//checking for any existing connections, if so, returns the connection
		
		if(connection!=null && !connection.isClosed()) {
			return connection;
		} else {
			//registering the Driver for this project connect
		try {
			Class.forName("org.postgresql.Driver"); //this is the base class for the postgres driver
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); //incase we can't find it, this will take care of it.
		}
		
		//url - javafs220725.clmfaswsjivh.us-west-1.rds.amazonaws.com
		
		String urlConnection = "jdbc:postgresql://"+con.getUrl()+":5432/"+con.getDatabase();
	//	String url = "jdbc:postgresql://javafs220725.clmfaswsjivh.us-west-1.rds.amazonaws.com:5432/project1";
	//	String username = "postgres";  // note - it is possible to hide raw credentials by using ENV variables
	//  System.getenv("password")			
	
		connection = DriverManager.getConnection(urlConnection, con.getUsername(), con.getPassword());

		return connection;
		
		}
	}
	
}

