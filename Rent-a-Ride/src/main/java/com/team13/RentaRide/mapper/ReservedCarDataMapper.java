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
import com.team13.RentaRide.model.ReservedCar;
import com.team13.RentaRide.tdgateway.ReservedCarTdGateway;
import com.team13.RentaRide.utils.DatabaseUtils;

public class ReservedCarDataMapper {
	ReservedCarTdGateway gateway = new ReservedCarTdGateway();

	public boolean addReservedCarRecord(ReservedCar reservedCar) {
		System.out.println("***reservedCar: " + reservedCar);
		try {
			HashMap<String, Object> parameterMap = new HashMap<>();

			parameterMap.put("CAR_ID", reservedCar.getCar().getId());
			parameterMap.put("CLIENT_ID", reservedCar.getAssociatedClient().getId());
			parameterMap.put("START_DATE", reservedCar.getStartDate());
			parameterMap.put("DUE_DATE", reservedCar.getDueDate());
			parameterMap.put("BOOKING_TIMESTAMP", reservedCar.getBookingTimestamp());

			return gateway.insertReservedCarRecord(parameterMap);
		} catch (Exception e) {
			System.out.println("Error while inserting a reserved car record in the database");
			e.printStackTrace();
			return false;
		}
	}

	public List<ReservedCar> getAllReservedCars() {

		ResultSet resultSet = gateway.getAllReservedCars();
		if (resultSet == null) {
			return new ArrayList<>();
		}

		List<ReservedCar> resCars = new ArrayList<>();
		try {
			resCars = parseResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return new ArrayList<>();
		}

		return resCars;

	}

	public List<ReservedCar> getAllReservedCars(LocalDate startDate, LocalDate dueDate) {

		ResultSet resultSet = gateway.getAllReservedCarsByDates(startDate, dueDate);
		if (resultSet == null) {
			return new ArrayList<>();
		}

		List<ReservedCar> resCars = new ArrayList<>();
		try {
			resCars = parseResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return new ArrayList<>();
		}

		return resCars;
	}

	private List<ReservedCar> parseResultSet(ResultSet resultSet) throws SQLException {
		List<ReservedCar> resCars = new ArrayList<>();
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

			Integer resCarId = resultSet.getInt(18);
			LocalDate startDateDb = resultSet.getDate(19).toLocalDate();
			LocalDate dueDateDb = resultSet.getDate(20).toLocalDate();
			Date bookingTimestamp = resultSet.getTimestamp(21);

			ReservedCar resCar = new ReservedCar(resCarId, car, client, startDateDb, dueDateDb, bookingTimestamp);
			resCars.add(resCar);

		}
		return resCars;
	}

	public void deleteCarReservationByLicense(String carLicencePlateNumber) {
		gateway.deleteCarReservation(carLicencePlateNumber);

	}

}
