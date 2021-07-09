package com.revature.daos;

import java.sql.*;

public class ConnectionUtil {
	
	public static Connection getConnection() throws SQLException {
			
			//For many frameworks using JDBC it is necessary to "register" the driver in order for the framework to be aware of it.
			try {
				Class.forName("org.postgresql.Driver");
			}catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			String url = "jdbc:postgresql://training-db.czhqtlklzwf7.us-east-2.rds.amazonaws.com:5432/postgres";
			String user = "postgres"; //It is possible to use environment variables to hide this information
			String pass = "password"; // you would access them with System.getenv("var-name")
			
			return DriverManager.getConnection(url, user, pass);
	}
	
}
