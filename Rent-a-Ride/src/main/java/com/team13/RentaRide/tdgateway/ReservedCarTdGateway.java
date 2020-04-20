package com.team13.RentaRide.tdgateway;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Map;

import com.team13.RentaRide.utils.DatabaseUtils;

/**
 * 
 * @author Admin
 *
 */

public class ReservedCarTdGateway {
	/**
	 * 
	 * @param parameterMap
	 * @return
	 */
	public boolean insertReservedCarRecord(Map<String, Object> parameterMap) {

		Connection connection = DatabaseUtils.getDbConnection();
		String query = "INSERT INTO ReservedCar VALUES (default, ?,?,?,?,?)";

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e1.printStackTrace();
			return false;
		}
		try {
			statement.setInt(1, (Integer) parameterMap.get("CAR_ID"));
			statement.setInt(2, (Integer) parameterMap.get("CLIENT_ID"));
			LocalDate startDate = (LocalDate) parameterMap.get("START_DATE");
			statement.setDate(3, java.sql.Date.valueOf(startDate));
			LocalDate dueDate = (LocalDate) parameterMap.get("DUE_DATE");
			statement.setDate(4, java.sql.Date.valueOf(dueDate));
			java.util.Date bookingDate = (java.util.Date) parameterMap.get("BOOKING_TIMESTAMP");
			Timestamp bookingTimestamp = new Timestamp(bookingDate.getTime());
			statement.setTimestamp(5, bookingTimestamp);
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
	public ResultSet getAllReservedCars() {

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
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query.toString());
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return resultSet;
	}

	/**
	 * 
	 * @param startDate
	 * @param dueDate
	 * @return
	 */
	public ResultSet getAllReservedCarsByDates(LocalDate startDate, LocalDate dueDate) {

		Connection connection = DatabaseUtils.getDbConnection();
		StringBuilder query = getSelectQuery();

		if (startDate != null && dueDate != null) {
			query.append("where start_date = ? and due_date = ?");
		}

		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query.toString());
			statement.setDate(1, Date.valueOf(startDate));
			statement.setDate(2, Date.valueOf(dueDate));
		} catch (SQLException e2) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e2.printStackTrace();
			return null;
		}
		try {
			statement.setDate(1, java.sql.Date.valueOf(startDate));
			statement.setDate(2, java.sql.Date.valueOf(dueDate));
		} catch (SQLException e1) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
			e1.printStackTrace();
		}

		ResultSet result = null;
		try {
			result = statement.executeQuery();
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return result;
	}

	/**
	 * 
	 * @return
	 */
	private StringBuilder getSelectQuery() {
		StringBuilder query = new StringBuilder();
		query.append("select car.*, client.*, ");
		query.append("res_car.id, res_car.start_date, res_car.due_date, res_car.booking_timestamp ");
		query.append("from reservedcar res_car ");
		query.append("join car on (res_car.car_id = car.id) ");
		query.append("join client on (res_car.client_id = client.id) ");
		return query;
	}

	public boolean deleteCarReservation(String carLicencePlateNumber) {

		String deleteQuery = "delete from reservedcar where car_id in (select id from car where license_plate_number = ?) ";
		Connection connection = DatabaseUtils.getDbConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(deleteQuery.toString());
			statement.setString(1, carLicencePlateNumber);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
		}

		try {
			statement.execute();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
		}

		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return true;

	}

}
