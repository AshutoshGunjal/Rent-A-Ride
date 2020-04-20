package com.team13.RentaRide.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.team13.RentaRide.model.Car;
import com.team13.RentaRide.tdgateway.CarTdGateway;
import com.team13.RentaRide.utils.DatabaseUtils;

public class CarDataMapper {

	CarTdGateway carGateway = new CarTdGateway();

	public boolean addCarRecord(Car carObj) {
		try {
			HashMap<String, Object> parameterMap = new HashMap<>();

			parameterMap.put("CAR_ID", carObj.getId());
			parameterMap.put("LICENSE_PLATE_NUMBER", carObj.getLicensePlateNumber());
			parameterMap.put("MAKE", carObj.getMake());
			parameterMap.put("MODEL", carObj.getModel());
			parameterMap.put("TYPE", carObj.getType());
			parameterMap.put("COLOR", carObj.getColor());
			parameterMap.put("YEAR", carObj.getYear());
			parameterMap.put("DESCRIPTION", carObj.getDescription());
			parameterMap.put("PRICE", carObj.getPrice());
			parameterMap.put("AVAILABLE_RESERVED_RENTED", carObj.getAvailableReservedOrRented());
			parameterMap.put("EDITING", carObj.isEditing());

			return carGateway.insertCarRecord(parameterMap);

		} catch (Exception e) {
			System.out.println("Error while inserting a reserved car record in the database");
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteCarRecord(String carLicensePlateNumber) {
		try {

			return carGateway.deleteCarRecord(carLicensePlateNumber);

		} catch (Exception e) {
			System.out.println("Error while deleting a car record in the database");
			e.printStackTrace();
			return false;
		}
	}

	public boolean modifyCarRecord(Car carObj) {
		System.out.println("carObj -- " + carObj);
		try {
			HashMap<String, Object> parameterMap = new HashMap<>();
		// when admin clicks edit
			if(carObj.isEditing() && carObj.getAvailableReservedOrRented().equals("Available") && carObj.getColor() != null) {
				parameterMap.put("EDITING", carObj.isEditing());
				parameterMap.put("LICENSE_PLATE_NUMBER", carObj.getLicensePlateNumber());

			}
//		// when clerk wants to rent/reserve a car
//			else if (carObj.isEditing() && carObj.getAvailableReservedOrRented().equals("Available") && carObj.getColor() != null) {
//				parameterMap.put("EDITING", carObj.isEditing());
//				parameterMap.put("LICENSE_PLATE_NUMBER", carObj.getLicensePlateNumber());
//			
//			}
		// when clerk reserves a car
			else if (carObj.isEditing() && carObj.getAvailableReservedOrRented().equals("Reserved") && carObj.getColor() == null) {
				parameterMap.put("AVAILABLE_RESERVED_RENTED", carObj.getAvailableReservedOrRented());
				parameterMap.put("EDITING", carObj.isEditing());
				parameterMap.put("LICENSE_PLATE_NUMBER", carObj.getLicensePlateNumber());
			
			} 
			// when clerk rents a car	
			else if (carObj.isEditing() && carObj.getAvailableReservedOrRented().equals("Rented") && carObj.getColor() == null) {
				parameterMap.put("AVAILABLE_RESERVED_RENTED", carObj.getAvailableReservedOrRented());
				parameterMap.put("EDITING", carObj.isEditing());
				parameterMap.put("LICENSE_PLATE_NUMBER", carObj.getLicensePlateNumber());
			
			} 
		
			//when car is res/rented and admin cancels modifying
			else if(carObj.isEditing() && (carObj.getAvailableReservedOrRented().equals("Rented") || carObj.getAvailableReservedOrRented().equals("Reserved") ) && carObj.getColor() != null ) {
				
				parameterMap.put("EDITING", carObj.isEditing());
				parameterMap.put("LICENSE_PLATE_NUMBER", carObj.getAvailableReservedOrRented());
			}
			
			
			// when admin saves after editing or cancelling
			else if (!carObj.isEditing() && carObj.getAvailableReservedOrRented().equals("Available") && carObj.getColor() != null){
				
				parameterMap.put("CAR_ID", carObj.getId());
				parameterMap.put("LICENSE_PLATE_NUMBER", carObj.getLicensePlateNumber());
				parameterMap.put("MAKE", carObj.getMake());
				parameterMap.put("MODEL", carObj.getModel());
				parameterMap.put("TYPE", carObj.getType());
				parameterMap.put("COLOR", carObj.getColor());
				parameterMap.put("YEAR", carObj.getYear());
				parameterMap.put("DESCRIPTION", carObj.getDescription());
				parameterMap.put("PRICE", carObj.getPrice());
				parameterMap.put("AVAILABLE_RESERVED_RENTED", carObj.getAvailableReservedOrRented());
				parameterMap.put("EDITING", carObj.isEditing());
			}
			return carGateway.modifyCarRecord(parameterMap);

		} catch (Exception e) {
			System.out.println("Error while modifying a car record in the database");
			e.printStackTrace();
			return false;
		}
	}

	public List<Car> getAllCars() {

		ResultSet resultSet = carGateway.findAllCars();

		if (resultSet == null) {
			return new ArrayList<Car>();
		}

		List<Car> allCars = new ArrayList<Car>();

		try {
			allCars = parseResultSet(resultSet);
		} catch (Exception e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return new ArrayList<Car>();
		}

		return allCars;

	}

	public List<Car> parseResultSet(ResultSet resultSet) throws SQLException {

		List<Car> allCars = new ArrayList<>();
		while (resultSet.next()) {
			Car car = getModelFromResultSet(resultSet);
			allCars.add(car);
		}
		return allCars;

	}

	private Car getModelFromResultSet(ResultSet resultSet) throws SQLException {
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
		return car;
	}

	public Car getCarByLicenseNumber(String licensePlateNumber) {
		ResultSet resultSet = carGateway.getCarByLicensePlateNumber(licensePlateNumber);
		try {
			resultSet.next();
			return getModelFromResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println("Error while setting parameters to ");
			e.printStackTrace();
			return null;
		}

	}

	public Car getNextCarFromId(Integer carId) {

		ResultSet resultSet = carGateway.getNextCarFromId(carId);
		try {
			return getModelFromResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println("Error while setting parameters to ");
			e.printStackTrace();
			return null;
		}
	}

}
