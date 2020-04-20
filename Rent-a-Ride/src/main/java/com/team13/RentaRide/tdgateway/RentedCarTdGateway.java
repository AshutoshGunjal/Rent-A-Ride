package com.team13.RentaRide.tdgateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

import com.team13.RentaRide.utils.DatabaseUtils;
/**
 * 
 * @author Admin
 *
 */

public class RentedCarTdGateway {
	/**
	 * 
	 * @param parameterMap
	 * @return
	 */
	
	public boolean insertRentedCarRecord(HashMap<String, Object> parameterMap) {
		
		Connection connection = DatabaseUtils.getDbConnection();
		
		String query = "INSERT INTO RentedCar VALUES (default , ?,?,?,?,?) ";
		
		PreparedStatement statement = null;
		System.out.println(parameterMap);
		try {
			
			statement = connection.prepareStatement(query);
			System.out.println(statement);
		} catch (Exception e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
		try {
			statement.setInt(1,(Integer) parameterMap.get("CAR_ID"));
			statement.setInt(2,(Integer) parameterMap.get("CLIENT_ID"));
			
			LocalDate start_date = (LocalDate) parameterMap.get("START_DATE");
			statement.setDate(3,java.sql.Date.valueOf(start_date));
			
			LocalDate due_date = (LocalDate) parameterMap.get("DUE_DATE");
			statement.setDate(4,java.sql.Date.valueOf(due_date));
			
			Date timeStampDateFormat = (Date) (parameterMap.get("BOOKING_TIMESTAMP"));
			
			Timestamp timeStamp = new Timestamp(timeStampDateFormat.getTime());
			statement.setTimestamp(5, timeStamp);
			
			
			
		} catch (Exception e) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
		try {
			statement.execute();
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		
		return true;
	}
	
	
	public boolean deleteRecord(String licensePlateNumber) {
		
		Connection connection = DatabaseUtils.getDbConnection();
		StringBuilder query = getDeleteQuery();
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(query.toString());
			
		} catch (Exception e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
			
		}
		try {
			statement.setString(1, licensePlateNumber);
				
		} catch (Exception e) {
			
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
		try {
			statement.execute();
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
	
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		
		return true;
		
	}
	
	
	public ResultSet findRentedCarByLicensePlateNumber( String licensePlateNumber) {
		Connection connection = DatabaseUtils.getDbConnection();
		StringBuilder query = getSelectQuery();
		
		query.append("where license_plate_number = ?");
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement(query.toString());
			
		} catch (Exception e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
			
		}
		try {
			statement.setString(1, licensePlateNumber);
			
		} catch (Exception e) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		ResultSet resultSet = null;
		try {
			resultSet =  statement.executeQuery();
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
	
		
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return resultSet;

		
	}
	
	public ResultSet selectAllRentedCars() {


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
	 * @return
	 */
	public ResultSet findRentedCarsByStartDate(LocalDate startDate) {
		
		Connection connection  = DatabaseUtils.getDbConnection();
		
		StringBuilder query = getSelectQuery();
		query.append("where start_date = ?");
		PreparedStatement statement=null;
		
		try {
			statement = connection.prepareStatement(query.toString());
		
		} catch (Exception e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		try {
			statement.setDate(1, java.sql.Date.valueOf(startDate));
			
		} catch (Exception e) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query.toString());
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
		}
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return resultSet;
		
		
	}

	/**
	 * 
	 * @param dueDate
	 * @return
	 */
	public ResultSet findRentedCarsByDueDate(LocalDate dueDate) {
		
		Connection connection  = DatabaseUtils.getDbConnection();
		
		StringBuilder query = getSelectQuery();
		query.append("where due_date = ?");
		PreparedStatement statement=null;
		
		try {
			statement = connection.prepareStatement(query.toString());
		
		} catch (Exception e) {
			System.out.println(DatabaseUtils.CREATE_STATEMENT_ERROR_MESSAGE);
			e.printStackTrace();
		}
		
		try {
			statement.setDate(1, java.sql.Date.valueOf(dueDate));
			
		} catch (Exception e) {
			System.out.println(DatabaseUtils.PARAMETER_ERROR_MESSAGE);
		}
		ResultSet resultSet = null;
		try {
			resultSet = statement.executeQuery(query.toString());
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
		}
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return resultSet;
		
		

	}	

	
	/**
	 * 
	 * @return
	 */

	private StringBuilder getSelectQuery() {
		StringBuilder query = new StringBuilder();
		query.append("select car.*, client.*, ");
		query.append("ren_car.id, ren_car.start_date, ren_car.due_date, ren_car.booking_timestamp ");
		query.append("from rentedcar ren_car ");
		query.append("join car on (ren_car.car_id = car.id) ");
		query.append("join client on (ren_car.client_id = client.id) ");
		return query;
	}
	
	private StringBuilder getDeleteQuery() {
		
		StringBuilder query = new StringBuilder();
		query.append("delete from rentedcar where car_id = (select id from car where license_plate_number = ?) ");
		
		return query;
	}
	

}
