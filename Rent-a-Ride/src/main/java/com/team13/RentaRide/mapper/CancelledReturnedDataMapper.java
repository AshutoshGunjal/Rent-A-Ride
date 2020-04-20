package com.team13.RentaRide.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.team13.RentaRide.model.CancelledReturnedBooking;
import com.team13.RentaRide.model.Car;
import com.team13.RentaRide.model.Client;
import com.team13.RentaRide.tdgateway.CancelledReturnedTdGateway;
import com.team13.RentaRide.utils.DatabaseUtils;

public class CancelledReturnedDataMapper {
	
	CancelledReturnedTdGateway gateway = new CancelledReturnedTdGateway();
	 
	public boolean addRecord(String carLicensePlateNumber,String ReturnOrCancel) {
		
		try {
			return gateway.addRecord(carLicensePlateNumber,ReturnOrCancel);
			
		} catch (Exception e) {
			
			return false;
		}
		
		
	}
	
	public List<CancelledReturnedBooking> getAllRecords (){
		
		ResultSet resultSet = gateway.getAllRecords();
		if (resultSet == null) {
			return new ArrayList<>();
		}

		List<CancelledReturnedBooking>  canRetCars = new ArrayList<>();
		try {
			canRetCars = parseResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return new ArrayList<>();
		}

		return canRetCars;

	}
	
	private List<CancelledReturnedBooking> parseResultSet(ResultSet resultSet) throws SQLException {
		List<CancelledReturnedBooking> canRetCars = new ArrayList<>();
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

			Integer CanRetCarId = resultSet.getInt(18);
			LocalDate startDateDb = resultSet.getDate(19).toLocalDate();
			LocalDate dueDateDb = resultSet.getDate(20).toLocalDate();
			Date bookingTimestamp = resultSet.getTimestamp(21);
			Date canRetTimestamp = resultSet.getTimestamp(22);

			CancelledReturnedBooking retCanCar = new CancelledReturnedBooking(CanRetCarId, car, client, startDateDb, dueDateDb , bookingTimestamp, canRetTimestamp);
			
			canRetCars.add(retCanCar);

		}
		return canRetCars;
	}
}
