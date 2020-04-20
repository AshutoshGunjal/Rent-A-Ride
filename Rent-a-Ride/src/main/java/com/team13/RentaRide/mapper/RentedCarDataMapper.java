package com.team13.RentaRide.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.team13.RentaRide.model.Car;
import com.team13.RentaRide.model.Client;
import com.team13.RentaRide.model.RentedCar;
import com.team13.RentaRide.tdgateway.RentedCarTdGateway;
import com.team13.RentaRide.utils.DatabaseUtils;

public class RentedCarDataMapper {

	RentedCarTdGateway gateway = new RentedCarTdGateway();

	public boolean addRentedCarRecord(RentedCar renCar) {

		try {
			HashMap<String, Object> parameterMap = new HashMap<String, Object>();

			parameterMap.put("CAR_ID", renCar.getCar().getId());
			parameterMap.put("CLIENT_ID", renCar.getAssociatedClient().getId());
			parameterMap.put("START_DATE", renCar.getStartDate());
			parameterMap.put("DUE_DATE", renCar.getDueDate());
			parameterMap.put("BOOKING_TIMESTAMP", renCar.getBookingTimestamp());
			
			return gateway.insertRentedCarRecord(parameterMap);
			
		} catch (Exception e) {
			System.out.println("Error while inserting rented car record");
			e.printStackTrace();
			return false;
		}

	}
	
	public List<RentedCar> getAllRentedCars() {

		ResultSet resultSet = gateway.selectAllRentedCars();
		if (resultSet == null) {
			return new ArrayList<>();
		}

		List<RentedCar> renCars = new ArrayList<>();
		try {
			renCars = parseResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return new ArrayList<>();
		}

		return renCars;

	}
	
	
	public boolean handleReturnOfVehicle(String licensePlateNumber) {
		
		try {
			return gateway.deleteRecord(licensePlateNumber);
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
	
	}
	
	
	
	public List<RentedCar> getRentedCarByLicensePlateNumber(String licensePlateNumber) {
		
		ResultSet resultSet = gateway.findRentedCarByLicensePlateNumber(licensePlateNumber);
		
		if (resultSet == null) {
			return new ArrayList<>();
		}
		
		List<RentedCar> renCars = new ArrayList<RentedCar>();
		
		try {
			renCars = parseResultSet(resultSet);
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		
		return renCars;
		
		
	}
	
	public List<RentedCar> getRentedCarsByStartDate(LocalDate startDate){
		
		ResultSet resultSet = gateway.findRentedCarsByStartDate(startDate);		
		if (resultSet == null) {
			return new ArrayList<>();
		}

		List<RentedCar> renCars = new ArrayList<RentedCar>();
		
		try {
			renCars = parseResultSet(resultSet);
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		
		
		
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return renCars;
		
	}
	
	
public List<RentedCar> getRentedCarsByDueDate(LocalDate dueDate){
		
		ResultSet resultSet = gateway.findRentedCarsByDueDate(dueDate);		
		if (resultSet == null) {
			return new ArrayList<>();
		}

		List<RentedCar> renCars = new ArrayList<RentedCar>();
		
		try {
			renCars = parseResultSet(resultSet);
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		
		
		
		System.out.println(DatabaseUtils.QUERY_SUCCESSFUL_MESSAGE);
		return renCars;
		
	}
	
	private List<RentedCar> parseResultSet(ResultSet resultSet) throws SQLException {
		List<RentedCar> renCars = new ArrayList<>();
		while (resultSet.next()) {
			Car car = new Car();
			car.setId(resultSet.getInt(1));
			car.setLicensePlateNumber(resultSet.getString(2));
			car.setMake(resultSet.getString(3));
			car.setModel(resultSet.getString(4));
			car.setType(resultSet.getString(5));
			car.setColor(resultSet.getString(6));
			car.setYear(resultSet.getInt(7));
			car.setDescription(resultSet.getString(8));
			car.setPrice(resultSet.getBigDecimal(9));
			car.setAvailableReservedOrRented(resultSet.getString(10));
			car.setEditing(resultSet.getBoolean(11));
			
			Client client = new Client();
			client.setId(resultSet.getInt(12));
			client.setDriverLicenceNumber(resultSet.getString(13));
			client.setClientFirstName(resultSet.getString(14));
			client.setClientLastName(resultSet.getString(15));
			client.setPhoneNumber(resultSet.getString(16));
			client.setLicenceExpiryDate(resultSet.getDate(17).toLocalDate());

			Integer renCarId = resultSet.getInt(18);
			LocalDate startDateDb = resultSet.getDate(19).toLocalDate();
			LocalDate dueDateDb = resultSet.getDate(20).toLocalDate();
			Date bookingTimestamp = resultSet.getTimestamp(21);

			RentedCar renCar = new RentedCar(renCarId, car, client, startDateDb, dueDateDb,  bookingTimestamp);
			renCars.add(renCar);

		}
		return renCars;
	}


}
