package com.team13.RentaRide.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtils {
	public static Connection dbConnection;

	public static final String CREATE_STATEMENT_ERROR_MESSAGE = "Error while creating statement";
	
	public static final String DELETE_STATEMENT_ERROR_MESSAGE = "Error while deleting statement";
	public static final String MODIFY_STATEMENT_ERROR_MESSAGE = "Error while modifying statement";


	public static final String PARAMETER_ERROR_MESSAGE = "Error while setting parameters to query";
	public static final String QUERY_EXECUTION_ERROR_MESSAGE = "Error while executing the query";
	public static final String QUERY_SUCCESSFUL_MESSAGE = "Query executed successfully";

	public static Connection getDbConnection() {
		return dbConnection;
	}

	public static void setDbConnection(Connection dbConnection) {
		DatabaseUtils.dbConnection = dbConnection;
	}

	public static void initConnection(String urlShort, String user, String password) {
		try {
			dbConnection = DriverManager.getConnection(urlShort, user, password);
		} catch (SQLException e) {

			System.out.println("Error while initiating connection with database");
			e.printStackTrace();
		}
	}

}
