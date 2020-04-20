package com.team13.RentaRide.tdgateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.team13.RentaRide.utils.DatabaseUtils;

public class CancelledReturnedTdGateway {
	
	public boolean addRecord(String licensePlateNumber,String ReturnOrCancel) {
		
		Connection connection = DatabaseUtils.getDbConnection();
		StringBuilder query = new StringBuilder();
		query.append("insert into cancelledreturned (car_id, client_id, start_date,due_date,booking_timestamp, cancelled_returned_timestamp) ")
			 .append(" select car_id, client_id, start_date,due_date,booking_timestamp, CURRENT_TIMESTAMP()");
			
			if(ReturnOrCancel == "Return") {
				 query.append(" from rentedcar");
			 }
			else if(ReturnOrCancel == "Cancel") {
				 query.append(" from reservedCar");
			 }
				 
			query.append(" where car_id = ( select id from car where license_plate_number = ? );" );
		
		PreparedStatement statement = null;
		
		try {
			
			statement = connection.prepareStatement(query.toString());
			System.out.println(statement);
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
	
	
	
	public ResultSet getAllRecords() {
		
		
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
	
	private StringBuilder getSelectQuery() {
		StringBuilder query = new StringBuilder();
		query.append("select car.*, client.*, ");
		query.append("ret_can_car.id, ret_can_car.start_date, ret_can_car.due_date, ret_can_car.booking_timestamp, ret_can_car.cancelled_returned_timestamp ");
		query.append("from cancelledReturned ret_can_car ");
		query.append("join car on (ret_can_car.car_id = car.id) ");
		query.append("join client on (ret_can_car.client_id = client.id) ");
		return query;
	}
	

}
