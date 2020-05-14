package final_project;

import java.sql.*;

public class ConnectToSQL {
	private Connection m_connection;
	
	public Connection getConnectToSQL(String login, String password) {
		try {
			String dbURL = "jdbc:sqlserver://LAPTOP-4UV8NE6L:1433;databaseName=OnlCourseManament";
			this.m_connection = DriverManager.getConnection(dbURL, login, password);
			
			System.out.println("Connected to SQL server.");
		} catch (SQLException ex) {
		     System.err.println("Cannot connect database, " + ex);
		}
		
		return m_connection;
	}
	
	public void Close() {
		try {
			if(!this.m_connection.isClosed()) 
				this.m_connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.m_connection = null;
	}
}
