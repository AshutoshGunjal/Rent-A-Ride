package com.team13.RentaRide.tdgateway;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
/**
 * Car Table data GateWay 
 * @author team 13
 */

import com.team13.RentaRide.utils.DatabaseUtils;

public class CarTdGateway {

	/**
	 * 
	 * @param parameterMap hashmap created to keep all car record content
	 * @throws SQLexception is thrown if the query fails expected execution.
	 * @return false if there is some error in query execution else true if the
	 *         query is executed successfully.
	 */

	public boolean insertCarRecord(Map<String, Object> parameterMap) {

		Connection connection = DatabaseUtils.getDbConnection();
		String query = "INSERT INTO Car VALUES (default, ?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e1.printStackTrace();
			return false;
		}
		try {

			statement.setString(1, (String) parameterMap.get("LICENSE_PLATE_NUMBER"));
			statement.setString(2, (String) parameterMap.get("MAKE"));
			statement.setString(3, (String) parameterMap.get("MODEL"));
			statement.setString(4, (String) parameterMap.get("TYPE"));

			statement.setString(5, (String) parameterMap.get("COLOR"));
			statement.setInt(6, (Integer) parameterMap.get("YEAR"));
			statement.setString(7, (String) parameterMap.get("DESCRIPTION"));
			statement.setBigDecimal(8, (BigDecimal) parameterMap.get("PRICE"));
			statement.setString(9, (String) parameterMap.get("AVAILABLE_RESERVED_RENTED"));
			statement.setBoolean(10, (Boolean) parameterMap.get("EDITING"));

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

	public boolean deleteCarRecord(String carLicencePlateNumber) {

		System.out.println("deleting car for carLicencePlateNumber: " + carLicencePlateNumber);
		Connection connection = DatabaseUtils.getDbConnection();
		String query = "DELETE from Car where LICENSE_PLATE_NUMBER = ?";

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
			System.out.println("delete car statement: " + statement);
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.DELETE_STATEMENT_ERROR_MESSAGE);
			e1.printStackTrace();
			return false;
		}
		try {

			statement.setString(1, carLicencePlateNumber);

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

	public boolean modifyCarRecord(Map<String, Object> parameterMap) {

		Connection connection = DatabaseUtils.getDbConnection();
		StringBuilder query = new StringBuilder();
		Integer count = 0;
		System.out.println("parameterMap.size()  " + parameterMap.size());
		
		if (parameterMap.size() == 2) {
			count = 2;
			
			query.append("UPDATE Car SET ").append("editing = ? ")
				 .append(" WHERE license_plate_number = ?");
			
			System.out.println("executing query: " + query);
		
		} 
		else if (parameterMap.size() == 3) {
			
			count = 3;
			
			query.append("UPDATE Car SET ").append("editing = ? , ")
			     .append("available_reserved_or_rented = ? " )
			     .append(" WHERE license_plate_number = ?");
		}
		
		else {
		
			query.append("UPDATE Car SET ").append(" make = ?,").append("  model = ?,").append(" type = ?,")
					.append(" color = ?,").append(" year = ?,").append(" description = ?,").append(" price = ?,")
					.append(" available_reserved_or_rented = ? ,").append(" editing = ? ")
					.append(" WHERE license_plate_number = ?");
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.MODIFY_STATEMENT_ERROR_MESSAGE);
			e1.printStackTrace();
			return false;
		}
		try {

			if (count == 2) {
				statement.setBoolean(1, (Boolean) parameterMap.get("EDITING"));
				statement.setString(2, (String) parameterMap.get("LICENSE_PLATE_NUMBER"));

			}
			else if ( count == 3) {
				
				statement.setBoolean(1, (Boolean) parameterMap.get("EDITING"));
				statement.setString(2, (String) parameterMap.get("AVAILABLE_RESERVED_RENTED"));
				statement.setString(3, (String) parameterMap.get("LICENSE_PLATE_NUMBER"));
			}
			else {
				statement.setString(1, (String) parameterMap.get("MAKE"));
				statement.setString(2, (String) parameterMap.get("MODEL"));
				statement.setString(3, (String) parameterMap.get("TYPE"));

				statement.setString(4, (String) parameterMap.get("COLOR"));
				statement.setInt(5, (Integer) parameterMap.get("YEAR"));
				statement.setString(6, (String) parameterMap.get("DESCRIPTION"));
				statement.setBigDecimal(7, (BigDecimal) parameterMap.get("PRICE"));
				statement.setString(8, (String) parameterMap.get("AVAILABLE_RESERVED_RENTED"));
				statement.setBoolean(9, (Boolean) parameterMap.get("EDITING"));
				statement.setString(10, (String) parameterMap.get("LICENSE_PLATE_NUMBER"));
				
			}

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

	public ResultSet findAllCars() {

		Connection connection = DatabaseUtils.getDbConnection();
		StringBuilder query = getSelectQuery();

		Statement statement = null;

		try {
			statement = connection.createStatement();
		} catch (Exception e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		ResultSet resultSet = null;

		try {
			resultSet = statement.executeQuery(query.toString());

		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;

		}

		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return resultSet;
	}

	private StringBuilder getSelectQuery() {
		StringBuilder query = new StringBuilder();
		query.append("select * from car");
		return query;

	}

	public ResultSet getCarByLicensePlateNumber(String licensePlateNumber) {

		System.out.println("***licensePlateNumber : " + licensePlateNumber);
		Connection connection = DatabaseUtils.getDbConnection();
		String query = "select * from car where license_plate_number = '" + licensePlateNumber + "'";

		Statement statement = null;
		try {
			statement = connection.createStatement();
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e1.printStackTrace();
			return null;
		}

		ResultSet result = null;
		try {
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}

		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return result;

	}

	public ResultSet getNextCarFromId(Integer carId) {

		String query = "select * from car where id = (select min(id) from car where id > ?)";
		Connection connection = DatabaseUtils.getDbConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e1.printStackTrace();
			return null;
		}
		try {
			statement.setInt(1, carId);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}

		ResultSet result = null;
		try {
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}

		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return result;

	}

	public ResultSet getPreviousCarFromId(Integer carId) {

		String query = "select * from car where id = (select max(id) from car where id < ?)";
		Connection connection = DatabaseUtils.getDbConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e1.printStackTrace();
			return null;
		}
		try {
			statement.setInt(1, carId);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}

		ResultSet result = null;
		try {
			result = statement.executeQuery(query);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}

		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return result;

	}

}
