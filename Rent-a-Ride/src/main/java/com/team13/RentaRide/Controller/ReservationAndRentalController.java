package com.team13.RentaRide.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team13.RentaRide.mapper.CancelledReturnedDataMapper;
import com.team13.RentaRide.mapper.CarDataMapper;
import com.team13.RentaRide.mapper.ClientDataMapper;
import com.team13.RentaRide.mapper.RentedCarDataMapper;
import com.team13.RentaRide.mapper.ReservedCarDataMapper;
import com.team13.RentaRide.model.Car;
import com.team13.RentaRide.model.Client;
import com.team13.RentaRide.model.RentedCar;
import com.team13.RentaRide.model.ReservedCar;

/**
 * 
 * @author Admin
 *
 */

@Controller

public class ReservationAndRentalController {

	private static final String CREATE_RENTAL = "CreateRental";
	private static final String CREATE_RESERVATION = "CreateReservation";
	ClientDataMapper clientDataMapper = new ClientDataMapper();
	CarDataMapper carDataMapper = new CarDataMapper();
	RentedCarDataMapper rentedCarDataMapper = new RentedCarDataMapper();
	CancelledReturnedDataMapper crDataMapper = new CancelledReturnedDataMapper();
	String returnOrCancel = null;
	String page = null;

	ReservedCarDataMapper reservedCarMapper = new ReservedCarDataMapper();

	/**
	 * 
	 * @param licensePlate
	 * @return
	 */

	@RequestMapping(value = "/rentThisCar")
	public ModelAndView showCarRentingPage(@RequestParam String licensePlate) {
		page = CREATE_RENTAL;
		
		Car c = carDataMapper.getCarByLicenseNumber(licensePlate);
		c.setEditing(true);
		c.setLicensePlateNumber(licensePlate);
		carDataMapper.modifyCarRecord(c);
		
		ModelAndView modelAndView=null;
		modelAndView = new ModelAndView("RentCarForClient");
		modelAndView.addObject("licensePlateNumber", licensePlate);
		modelAndView.addObject("pickUpDate", LocalDate.now());
		return modelAndView;
	}

	/**
	 * 
	 * @param licensePlate
	 * @return
	 */
	@RequestMapping(value = "/reserveThisCar")
	public ModelAndView showCarReservingPage(@RequestParam String licensePlate) {
		page = CREATE_RESERVATION;
		
		
		Car c = carDataMapper.getCarByLicenseNumber(licensePlate);
		c.setEditing(true);
		c.setLicensePlateNumber(licensePlate);
		carDataMapper.modifyCarRecord(c);
		
		ModelAndView modelAndView=null;
		modelAndView = new ModelAndView("ReserveCarForClient");
		modelAndView.addObject("licensePlateNumber", licensePlate);
		return modelAndView;

	}

	/**
	 * 
	 * @param driverLicenceNumber
	 * @param CarLicenseNo
	 * @return
	 */
	@RequestMapping(value = "/searchThisClient", method = RequestMethod.POST)
	public ModelAndView searchClient(@RequestParam String driverLicenceNumber, @RequestParam String CarLicenseNo) {
		ModelAndView modelAndView = null;
		System.out.println(CarLicenseNo);

		Client client = clientDataMapper.getClientByDrivingLicense(driverLicenceNumber);
		System.out.println("page: " + page);

		if (client != null) {
			if (page.equals(CREATE_RESERVATION)) {
				modelAndView = new ModelAndView("ReserveCarForClient");

			} else if (page.equals(CREATE_RENTAL)) {
				modelAndView = new ModelAndView("RentCarForClient");
				modelAndView.addObject("pickUpDate", LocalDate.now());
			}
			modelAndView.addObject("licensePlateNumber", CarLicenseNo);
			modelAndView.addObject("driverLicenseNumber", client.getDriverLicenceNumber());
			modelAndView.addObject("clientFirstName", client.getClientFirstName());
			modelAndView.addObject("clientLastName", client.getClientLastName());
			modelAndView.addObject("phoneNumber", client.getPhoneNumber());
			modelAndView.addObject("licenceExpiryDate", client.getLicenceExpiryDate());
			return modelAndView;
		} else {
			if (page.equals(CREATE_RESERVATION)) {
				modelAndView = new ModelAndView("ReserveCarForClient");
			} else if (page.equals(CREATE_RENTAL)) {
				modelAndView = new ModelAndView("RentCarForClient");
				modelAndView.addObject("pickUpDate", LocalDate.now());
			}

			modelAndView.addObject("licensePlateNumber", CarLicenseNo);
			modelAndView.addObject("driverLicenseNumber", driverLicenceNumber);
			modelAndView.addObject("clientNotFoundMessage",
					"Client does not exist already. Please enter the client details manually");
			return modelAndView;
		}

	}

	/**
	 * 
	 * @param clientFirstName
	 * @param clientLastName
	 * @param phoneNumber
	 * @param driverLicenceNumber
	 * @param licenceExpiryDate
	 * @param CarLicenseNo
	 * @param dropoffDate
	 * @param pickupDate
	 * @return
	 */
	@RequestMapping(value = "/carReserved", method = RequestMethod.POST)
	public ModelAndView showReservedCars(@RequestParam String clientFirstName, @RequestParam String clientLastName,
			@RequestParam String phoneNumber, @RequestParam String driverLicenceNumber,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate licenceExpiryDate,

			@RequestParam String CarLicenseNo,

			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dropoffDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate pickupDate) {

		Car selectedCar = carDataMapper.getCarByLicenseNumber(CarLicenseNo);
		if (selectedCar == null) {
			System.out.println("No car found with license " + CarLicenseNo);
			return null;
		}

		Client client = clientDataMapper.getClientByDrivingLicense(driverLicenceNumber);
		if (client == null) {
			client = createNewClient(clientFirstName, clientLastName, phoneNumber, driverLicenceNumber,
					licenceExpiryDate);
			// insert client record in db, and fetch it again from db to obtain the id...
			System.out.println("adding client " + client);
			clientDataMapper.addClientRecord(client);
			client = clientDataMapper.getClientByDrivingLicense(client.getDriverLicenceNumber());
			System.out.println("created client : " + client);

		}

		ReservedCar reservedCar = createReservedCar(dropoffDate, pickupDate, selectedCar, client);
		reservedCarMapper.addReservedCarRecord(reservedCar);
		
		Car c = new Car();
		c.setAvailableReservedOrRented("Reserved");
		c.setEditing(true);
		c.setLicensePlateNumber(CarLicenseNo);

		carDataMapper.modifyCarRecord(c);
		List<ReservedCar> rescars =  reservedCarMapper.getAllReservedCars();
		return new ModelAndView("ViewReservedTransactions", "reservations",rescars);

	}

	private ReservedCar createReservedCar(LocalDate dropoffDate, LocalDate pickupDate, Car selectedCar, Client client) {
		ReservedCar reservedCar = new ReservedCar(null, selectedCar, client, pickupDate, dropoffDate, new Date());

		Date d1; 
		Date d2;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = null;
		d1 = new Date();
		d = sdf.format(d1);
		try {
			d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
			reservedCar.setBookingTimestamp(d2);

		} catch (ParseException e) {
			System.out.println("Error while parsing booking date");
			e.printStackTrace();
			return null;
		}
		return reservedCar;
	}

	/**
	 * 
	 * @param carLicencePlateNumber
	 * @return
	 */
	@RequestMapping(value = "/cancelThisReservation")
	public ModelAndView cancelSelectedReservation(@RequestParam String carLicencePlateNumber) {

		returnOrCancel = "Cancel";
		crDataMapper.addRecord(carLicencePlateNumber, returnOrCancel);
		
		reservedCarMapper.deleteCarReservationByLicense(carLicencePlateNumber);
		List<ReservedCar> resCars = reservedCarMapper.getAllReservedCars();
		
		Car c = carDataMapper.getCarByLicenseNumber(carLicencePlateNumber);
		c.setAvailableReservedOrRented("Available");
		c.setEditing(false);
		
		carDataMapper.modifyCarRecord(c);
		
		return new ModelAndView("ViewReservedTransactions", "reservations", resCars);
	}

	/**
	 * 
	 * @param carLicencePlateNumber
	 * @return
	 */
	@RequestMapping(value = "/handleTheReturnThisRental")
	public ModelAndView returnSelectedRental(@RequestParam String carLicensePlateNumber) {

		returnOrCancel = "Return";
		crDataMapper.addRecord(carLicensePlateNumber, returnOrCancel);

		rentedCarDataMapper.handleReturnOfVehicle(carLicensePlateNumber);
		
		Car c = carDataMapper.getCarByLicenseNumber(carLicensePlateNumber);
		c.setAvailableReservedOrRented("Available");
		c.setEditing(false);
		
		carDataMapper.modifyCarRecord(c);

		ModelAndView modelAndView = new ModelAndView("ViewRentalTransactions", "rentals", rentedCarDataMapper.getAllRentedCars());
		return modelAndView;
	}

	/**
	 * 
	 * @param clientFirstName
	 * @param clientLastName
	 * @param phoneNumber
	 * @param driverLicenceNumber
	 * @param licenceExpiryDate
	 * @param CarLicenseNo
	 * @param dropoffDate
	 * @param pickupDate
	 * @return
	 */
	@RequestMapping(value = "/carRented", method = RequestMethod.POST)
	public ModelAndView showRentedCars(@RequestParam String clientFirstName, @RequestParam String clientLastName,
			@RequestParam String phoneNumber, @RequestParam String driverLicenceNumber,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate licenceExpiryDate,
			@RequestParam String CarLicenseNo,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dropoffDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate pickupDate) {

		Car selectedCar = carDataMapper.getCarByLicenseNumber(CarLicenseNo);
		if (selectedCar == null) {
			System.out.println("No car found with license " + CarLicenseNo);
			return null;
		}
		Client selectedClient = clientDataMapper.getClientByDrivingLicense(driverLicenceNumber);
		if (selectedClient == null) {
			selectedClient = createNewClient(clientFirstName, clientLastName, phoneNumber, driverLicenceNumber,
					licenceExpiryDate);
			
			System.out.println("adding client " + selectedClient);
			clientDataMapper.addClientRecord(selectedClient);
			selectedClient = clientDataMapper.getClientByDrivingLicense(selectedClient.getDriverLicenceNumber());
			System.out.println("created client : " + selectedClient);
		}

		RentedCar rentedCar = createRentedCarObject(dropoffDate, pickupDate, selectedCar, selectedClient);
		rentedCarDataMapper.addRentedCarRecord(rentedCar);
		Car c = new Car();
		c.setEditing(true);
		c.setAvailableReservedOrRented("Rented");
		c.setLicensePlateNumber(CarLicenseNo);

		carDataMapper.modifyCarRecord(c);
		return new ModelAndView("ViewRentalTransactions", "rentals", rentedCarDataMapper.getAllRentedCars());

	}

	private RentedCar createRentedCarObject(LocalDate dropoffDate, LocalDate pickupDate, Car selectedCar,
			Client selectedClient) {
		RentedCar renCar = new RentedCar(null, selectedCar, selectedClient, pickupDate, dropoffDate, new Date());
		Date d1;
		Date d2;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = null;
		d1 = new Date();
		d = sdf.format(d1);
		try {
			d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
			renCar.setBookingTimestamp(d2);
		} catch (ParseException e) {
			System.out.println("Error while setting booking date time");
			e.printStackTrace();
		}
		return renCar;
	}

	private Client createNewClient(String clientFirstName, String clientLastName, String phoneNumber,
			String driverLicenceNumber, LocalDate licenceExpiryDate) {
		Client selectedClient;
		selectedClient = new Client();
		selectedClient.setClientFirstName(clientFirstName);
		selectedClient.setClientLastName(clientLastName);
		selectedClient.setPhoneNumber(phoneNumber);
		selectedClient.setDriverLicenceNumber(driverLicenceNumber);
		selectedClient.setLicenceExpiryDate(licenceExpiryDate);
		return selectedClient;
	}

	@RequestMapping(value = "/backToCarCatalog")
	public ModelAndView goToCarCatalog() {

		ModelAndView modelAndView = new ModelAndView("CarCatalog");
		modelAndView.addObject("cars", carDataMapper.getAllCars());
		return modelAndView;

	}

	@RequestMapping(value = "/backToRentedCarList")
	public ModelAndView showRentedCarsPage() {

		ModelAndView modelAndView = new ModelAndView("ViewRentalTransactions");
		
		modelAndView.addObject("rentals", rentedCarDataMapper.getAllRentedCars());
		return modelAndView;

	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/backToReservedCarList")
	public ModelAndView showReservedCarsPage() {

		ModelAndView modelAndView = new ModelAndView("ViewReservedTransactions");

		List<ReservedCar> rescars =  reservedCarMapper.getAllReservedCars();
		modelAndView.addObject("reservations",rescars);
		return modelAndView;

	}

}
