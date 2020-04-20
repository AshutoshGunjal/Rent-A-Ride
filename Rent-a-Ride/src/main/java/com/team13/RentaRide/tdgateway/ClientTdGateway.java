package com.team13.RentaRide.tdgateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.team13.RentaRide.utils.DatabaseUtils;

/**
 * 
 * @author Admin
 *
 */

public class ClientTdGateway {
	/**
	 * 
	 * @param client_parameterMap
	 * @return
	 */
	public boolean insertClientRecord(Map<String, Object> client_parameterMap) {

		Connection connection = DatabaseUtils.getDbConnection();
		String query = "INSERT INTO Client VALUES (default, ?,?,?,?,?,?)";

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e1.printStackTrace();
			return false;
		}
		try {

			statement.setString(1, (String) client_parameterMap.get("DRIVER_LICENCE_NUMBER"));
			statement.setString(2, (String) client_parameterMap.get("CLIENT_FIRST_NAME"));
			statement.setString(3, (String) client_parameterMap.get("CLIENT_LAST_NAME"));
			statement.setString(4, (String) client_parameterMap.get("CLIENT_PHONE_NUMBER"));
			LocalDate licenceExpiryDate = (LocalDate) client_parameterMap.get("CLIENT_LICENCE_EXPIRY_DATE");
			statement.setDate(5, java.sql.Date.valueOf(licenceExpiryDate));
			statement.setBoolean(6, (Boolean) client_parameterMap.get("EDITING")); 
			
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}

		try {
			statement.execute();
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}

		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return true;
	}

	public boolean deleteClient(String driverLicenceNumber) {
		Connection connection = DatabaseUtils.getDbConnection();
		StringBuilder query = getDeleteQuery();

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		try {
			statement.setString(1, driverLicenceNumber);

		} catch (Exception e) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
		}

		try {
			statement.execute();
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return true;

	}

	public boolean modifyClient(HashMap<String, Object> client_parameterMap) {
		Connection connection = DatabaseUtils.getDbConnection();
		StringBuilder query = getUpdateQuery();

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e1.printStackTrace();
			return false;
		}

		try {
			statement.setString(1, (String) client_parameterMap.get("CLIENT_FIRST_NAME"));
			statement.setString(2, (String) client_parameterMap.get("CLIENT_LAST_NAME"));
			statement.setString(3, (String) client_parameterMap.get("CLIENT_PHONE_NUMBER"));
			LocalDate licenceExpiryDate = (LocalDate) client_parameterMap.get("CLIENT_LICENCE_EXPIRY_DATE");
			statement.setDate(4, java.sql.Date.valueOf(licenceExpiryDate));
			statement.setString(6, (String) client_parameterMap.get("DRIVER_LICENCE_NUMBER"));
			statement.setBoolean(5, (Boolean) client_parameterMap.get("EDITING"));

		} catch (SQLException e) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}

		try {
			statement.execute();
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}

		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public ResultSet getAllClients() {

		Connection connection = DatabaseUtils.getDbConnection();
		StringBuilder query = getSelectQuery();

		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		ResultSet client_resultSet = null;
		try {
			client_resultSet = statement.executeQuery(query.toString());
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return client_resultSet;
	}

	private StringBuilder getSelectQuery() {
		StringBuilder view_client = new StringBuilder();
		view_client.append("select * from client");
		return view_client;
	}

	/**
	 * 
	 * @return
	 */

	private StringBuilder getUpdateQuery() {
		StringBuilder update_client = new StringBuilder();
		update_client.append(
				"UPDATE Client SET client_first_name = ?, client_last_name = ?, phone_number = ?, licence_expiry_date = ? , editing = ? WHERE driver_licence_number = ? ");
		return update_client;
	}

	/**
	 * 
	 * @return
	 */

	private StringBuilder getDeleteQuery() {
		StringBuilder delete_client = new StringBuilder();
		delete_client.append("DELETE FROM Client WHERE driver_licence_number = ?");
		return delete_client;
	}

	public ResultSet getClientByDrivingLicense(String driverLicenceNumber) {

		String query = "select * from client where driver_licence_number = '" + driverLicenceNumber + "'";
		Connection connection = DatabaseUtils.getDbConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		ResultSet client = null;
		try {
			client = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return client;
	}

}
