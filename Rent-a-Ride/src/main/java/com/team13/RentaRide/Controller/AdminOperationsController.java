package com.team13.RentaRide.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team13.RentaRide.mapper.AdminDataMapper;
import com.team13.RentaRide.mapper.CancelledReturnedDataMapper;
import com.team13.RentaRide.mapper.CarDataMapper;
import com.team13.RentaRide.mapper.RentedCarDataMapper;
import com.team13.RentaRide.mapper.ReservedCarDataMapper;
import com.team13.RentaRide.model.Admin;
import com.team13.RentaRide.model.CancelledReturnedBooking;
import com.team13.RentaRide.model.Car;
import com.team13.RentaRide.model.RentedCar;
import com.team13.RentaRide.model.ReservedCar;

/**
 * The controller for Admin Operations.
 * 
 * @author team 13
 *
 */

@Controller
public class AdminOperationsController {

	private boolean isAnyAdminLoggedIn=false;
	private CarDataMapper carDataMapper = new CarDataMapper();
	private CancelledReturnedDataMapper crDataMapper = new CancelledReturnedDataMapper();
	private RentedCarDataMapper rentedCarDataMapper = new RentedCarDataMapper();
	Car editingCar = null;

	/**
	 * <p>
	 * The different Admin operations controlled by this classes are mentioned as
	 * follows
	 * </p>
	 * <ol>
	 * <li>Successful login of Admin</li>
	 * <li>Manage the Car catalog</li>
	 * <li>Viewing Rental Cars</li>
	 * <li>Viewing Reserved Cars</li>
	 * <li>Viewing Returned Cars</li>
	 * <li>Viewing Cancelled Cars</li>
	 */

	private AdminDataMapper adminDataMapper = new AdminDataMapper();
	private ReservedCarDataMapper reservedCarMapper = new ReservedCarDataMapper();

	@RequestMapping("/AdminLoginPage")
	public ModelAndView showLoginPage() {
		
		return new ModelAndView("AdminLoginPage");
	}


	@RequestMapping("/adminViewRentals")
	public ModelAndView showRentals() {

		ModelAndView modelAndView = new ModelAndView("AdminViewRentalTransactions", "rentals",
				rentedCarDataMapper.getAllRentedCars());

		return modelAndView;
	}

	@RequestMapping("/adminViewReservations")
	public ModelAndView showReservations() {

		List<ReservedCar> cars = reservedCarMapper.getAllReservedCars();

		return new ModelAndView("AdminViewReservedTransactions", "reservations", cars);
	}

	@RequestMapping(value = "/tryToLoginAsAdmin", method = RequestMethod.POST)
	public ModelAndView showWelcomePage(@RequestParam String email, @RequestParam String password) {
		System.out.println("here**************");
		try {
			if(isAnyAdminLoggedIn) {
				ModelAndView modelAndView = new ModelAndView("AdminLoginPage");
				modelAndView.addObject("errorMessage","Cannot Login .. Another Admin is currently logged in!!");
				return modelAndView;
			}
			else {
				boolean flag = false;
				List<Admin> admins = adminDataMapper.getAllAdminRecords();
				System.out.println("admins: " + admins);

				for (Admin admin : admins) {
					if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
						flag = true;
						isAnyAdminLoggedIn = true;
						break;
					}
				}

				if (flag) {
					ModelAndView modelAndView = new ModelAndView("AdminHomePage");
					return modelAndView;

				}
				ModelAndView modelAndView = new ModelAndView("AdminLoginPage");
				modelAndView.addObject("errorMessage", "INVALID LOGIN! Please try again.");
				return modelAndView;

			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@RequestMapping(value = "/registerAdminPage")
	/**
	 * This calls the registration page if the admin is logging the system for the
	 * first time.
	 * 
	 * @param email    : email id of the Admin.
	 * @param password : password of the Admin.
	 * @return
	 */
	public ModelAndView registerAdminPage() {

		return new ModelAndView("AdminRegisterPage");

		
//		
//		
//		if (!email.isEmpty() && !password.isEmpty()) {
//			Admin admin = new Admin(email, password);
//			adminDataMapper.addAdminRecord(admin);
//			return new ModelAndView("AdminLoginPage");
//		}
//
//		ModelAndView modelAndView = new ModelAndView("AdminRegisterPage");
//		modelAndView.addObject("errorMessage", "Invalid UserId/Password, try again!");
//		return modelAndView;

	}
	@RequestMapping(value = "/registerAdmin", method = RequestMethod.POST)
	/**
	 * This calls the registration page if the admin is logging the system for the
	 * first time.
	 * 
	 * @param email    : email id of the Admin.
	 * @param password : password of the Admin.
	 * @return
	 */
	public ModelAndView registerAdmin(@RequestParam String email, @RequestParam String password) {

		
		if (!email.isEmpty() && !password.isEmpty()) {
			Admin admin = new Admin(email, password);
			adminDataMapper.addAdminRecord(admin);
			return new ModelAndView("AdminLoginPage");
		}

		ModelAndView modelAndView = new ModelAndView("AdminRegisterPage");
		modelAndView.addObject("errorMessage", "Invalid UserId/Password, try again!");
		return modelAndView;

	}

	@RequestMapping(value = "/adminManageCatalog", method = RequestMethod.GET)
	public ModelAndView adminManageCatalog() {
		return new ModelAndView("AdminCarCatalogPage", "cars", carDataMapper.getAllCars());
	}

	@RequestMapping(value = "/addNewCarPage", method = RequestMethod.POST)
	public ModelAndView addNewCarPage() {

		return new ModelAndView("CreateNewCarPage");
	}

	@RequestMapping(value = "/addNewCar", method = RequestMethod.POST)
	/**
	 * 
	 * @param licensePlateNumber licence plate number of the car.
	 * @param carDescription     proper description of car like company name etc.
	 * @param carModel           model name of the car
	 * @param carType            type of the car like SUV,Sedan etc.
	 * @param carMake            manufacturer of the car.
	 * @param carYear            year that car was manufactured etc.
	 * @param carColor           color of the car.
	 * @param carPrice           price of the car.
	 * @return model and View after adding a new car.
	 */
	public ModelAndView addNewCar(@RequestParam String licensePlateNumber, @RequestParam String carDescription,
			@RequestParam String carModel, @RequestParam String carType, @RequestParam String carMake,
			@RequestParam String carYear, @RequestParam String carColor, @RequestParam String carPrice) {

		try {
			Car car = new Car();
			car.setAvailableReservedOrRented("Available");
			car.setColor(carColor);
			car.setDescription(carDescription);
			car.setLicensePlateNumber(licensePlateNumber);
			car.setMake(carMake);
			car.setModel(carModel);
			car.setPrice(new BigDecimal(carPrice));
			car.setType(carType);
			car.setYear(Integer.valueOf(carYear));
			car.setEditing(false);
			System.out.println("created car: " + car);

			carDataMapper.addCarRecord(car);
			return new ModelAndView("AdminCarCatalogPage", "cars", carDataMapper.getAllCars());

		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("errorMessage", "Error while adding the Car, " + e.getMessage());
			return modelAndView;
		}
	}

	@RequestMapping(value = "/editCar")
	/**
	 * to modify a car licence plate number is taken as a reference key based on
	 * which other all attributes of car will get modified.
	 * 
	 * @param currentLicensePlateNumber the licence plate number of the car that
	 *                                  needs to be modified is fetched.
	 * @return model and view of the modified car.
	 */
	public ModelAndView editCar(@RequestParam String currentLicensePlateNumber) {
		
		System.out.println("licensePlateNumber: " + currentLicensePlateNumber);
		Car currentCar = null;
		boolean flag = false;
		ModelAndView modelAndView = null;
		for (Car car : carDataMapper.getAllCars()) {
			if (car.getLicensePlateNumber().equals(currentLicensePlateNumber)) {
				currentCar = car;
				editingCar = car;
				break;
			}
		}
		Car selectedCar = null;
		if(! CarDetailsController.currentlySelectedCar.isEmpty()) {
			selectedCar = CarDetailsController.currentlySelectedCar.get(0);
			if(selectedCar.getLicensePlateNumber().equals(currentCar.getLicensePlateNumber())) {
				flag = true;
			}
		}


		if(currentCar.getAvailableReservedOrRented().equals("Reserved") || currentCar.getAvailableReservedOrRented().equals("Rented")) {
			flag = true;
		}

		List<RentedCar> renCars = rentedCarDataMapper.getAllRentedCars();
		List<ReservedCar> resCars = reservedCarMapper.getAllReservedCars();
		for (ReservedCar reservedCar : resCars) {
			if (reservedCar.getCar().equals(currentCar)) {
				flag = true;
				break;
			}
		}
		for (RentedCar rentedCar : renCars) {
			if (rentedCar.getCar().equals(currentCar)) {
				flag = true;
				break;
			}
		}

		modelAndView = new ModelAndView("EditCarPage", "car", currentCar);
		if (flag) {

			modelAndView = new ModelAndView("AdminCarCatalogPage", "cars", carDataMapper.getAllCars());
			modelAndView.addObject("errorMessage", "Cannot Edit Car Details, This car is currently Unavailable");
			return modelAndView;
			
//			modelAndView.addObject("editDecide", "Cannot Edit Car Details, Car curently Unavailable");
//
//			modelAndView.addObject("readOnly", "readonly");
//			modelAndView.addObject("disableOrNo", "disabled");

		} else {

			currentCar.setEditing(true);
			currentCar.setLicensePlateNumber(currentLicensePlateNumber);
			carDataMapper.modifyCarRecord(currentCar);

			modelAndView.addObject("editDecide", "Edit Car Details");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/saveCarChanges", method = RequestMethod.POST)
	public ModelAndView saveCarChanges(Car car) {
		car.setEditing(false);
		carDataMapper.modifyCarRecord(car);
		return new ModelAndView("AdminCarCatalogPage", "cars", carDataMapper.getAllCars());
	}

	@RequestMapping(value = "/deleteCar")
	public ModelAndView deleteCar(@RequestParam String currentLicensePlateNumber) {

		boolean flag = false;
		ModelAndView modelAndView = null;
		Car currentCar = null;
		for (Car car : carDataMapper.getAllCars()) {
			if (car.getLicensePlateNumber().equals(currentLicensePlateNumber)) {
				currentCar = car;
				break;
			}
		}
		Car selectedCar = null;
		if(! CarDetailsController.currentlySelectedCar.isEmpty()) {
			selectedCar = CarDetailsController.currentlySelectedCar.get(0);
			if(selectedCar.getLicensePlateNumber().equals(currentCar.getLicensePlateNumber())) {
				flag = true;
			}
		}

		if(currentCar.getAvailableReservedOrRented().equals("Reserved") || currentCar.getAvailableReservedOrRented().equals("Rented")) {
			flag = true;
		}

		List<RentedCar> renCars = rentedCarDataMapper.getAllRentedCars();
		List<ReservedCar> resCars = reservedCarMapper.getAllReservedCars();

		for (ReservedCar reservedCar : resCars) {
			if (reservedCar.getCar().equals(currentCar)) {
				flag = true;
				break;
			}
		}
		for (RentedCar rentedCar : renCars) {
			if (rentedCar.getCar().equals(currentCar)) {
				flag = true;
				break;
			}
		}
		if(currentCar.isEditing()) {
			flag = true ;
		}
		if (flag) {
			modelAndView = new ModelAndView("AdminCarCatalogPage", "cars", carDataMapper.getAllCars());
			modelAndView.addObject("errorMessage", "Cannot delete vehicle now  as it is currently booked/under booking");
			return modelAndView;
		} else {

			carDataMapper.deleteCarRecord(currentLicensePlateNumber);
			return new ModelAndView("AdminCarCatalogPage", "cars", carDataMapper.getAllCars());
		}
	}

	@RequestMapping(value = "/backToAdminReservedCarList")
	public ModelAndView showAdminReservedCarsPage() {

		ModelAndView modelAndView = new ModelAndView("AdminViewReservedTransactions");
		modelAndView.addObject("reservations", reservedCarMapper.getAllReservedCars());
		return modelAndView;

	}

	@RequestMapping(value = "/backToAdminRentedCarList")
	public ModelAndView showAdminRentedCarsPage() {

		ModelAndView modelAndView = new ModelAndView("AdminViewRentalTransactions");
		modelAndView.addObject("rentals", rentedCarDataMapper.getAllRentedCars());
		return modelAndView;

	}

	@RequestMapping(value = "/backToAdminCarCatalog")
	public ModelAndView goToAdminCarCatalog() {

		ModelAndView modelAndView = new ModelAndView("AdminCarCatalogPage", "cars", carDataMapper.getAllCars());

		List<Car> carsList = carDataMapper.getAllCars();

		modelAndView.addObject("cars", carsList);

		return modelAndView;
	}

	@RequestMapping(value = "/filterReservationRecordsForAdmin", method = RequestMethod.POST)
	/**
	 * to search a car using filters in reservation records.
	 * 
	 * @param licensePlateNumberInput   the licence plate number of the reserved car
	 *                                  that needs to be filtered is fetched.
	 * @param drivingLicenseNumberInput the driving licence number of the reserved
	 *                                  car that needs to be filtered is fetched.
	 * @param dueDateFilter             the due date of the reserved car that needs
	 *                                  to be filtered is fetched.
	 * @param pickupDateFilter          the pickup date of the reserved car that
	 *                                  needs to be filtered is fetched.
	 * @return model and view of reserved car after applying filters for reserved
	 *         cars.
	 */
	public ModelAndView filterReservationRecordsForAdmin(@RequestParam String licensePlateNumberInput,
			@RequestParam String drivingLicenseNumberInput,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueDateFilter,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate pickupDateFilter) {

		System.out.println("licensePlateNumberInput: " + licensePlateNumberInput);
		ModelAndView modelAndView = new ModelAndView("AdminViewReservedTransactions");

		List<ReservedCar> reservedCars = reservedCarMapper.getAllReservedCars();

		List<ReservedCar> reservedCarsToSend = new ArrayList<>();

		for (ReservedCar car : reservedCars) {

			if (!StringUtils.isEmpty(licensePlateNumberInput)
					&& !licensePlateNumberInput.equals(car.getCar().getLicensePlateNumber())) {
				continue;
			}

			if (!StringUtils.isEmpty(drivingLicenseNumberInput)
					&& !drivingLicenseNumberInput.equals(car.getAssociatedClient().getDriverLicenceNumber())) {
				continue;
			}

			if (dueDateFilter != null && !dueDateFilter.equals(car.getDueDate())) {
				continue;
			}

			if (pickupDateFilter != null && !pickupDateFilter.equals(car.getStartDate())) {
				continue;
			}
			reservedCarsToSend.add(car);

		}

		modelAndView.addObject("reservations", reservedCarsToSend);
		return modelAndView;

	}

	/**
	 * 
	 * @param licensePlateNumberInput
	 * @param drivingLicenseNumberInput
	 * @param dueDateFilter
	 * @param pickupDateFilter
	 * @return ======= /** to search a car using filters in reservation records.
	 * @param licensePlateNumberInput   the licence plate number of the rented car
	 *                                  that needs to be filtered is fetched.
	 * @param drivingLicenseNumberInput the driving licence number of the rented car
	 *                                  that needs to be filtered is fetched.
	 * @param dueDateFilter             the due date of the rented car that needs to
	 *                                  be filtered is fetched.
	 * @param pickupDateFilter          the pickup date of the rented car that needs
	 *                                  to be filtered is fetched.
	 * @return model and view of reserved car after applying filters for rented
	 *         cars. >>>>>>> 5eeb06941633f1fb1c013707f9da9095b589b3c8
	 */
	@RequestMapping(value = "/filterRentalRecordsForAdmin", method = RequestMethod.POST)

	public ModelAndView filterRentalRecordsForAdmin(@RequestParam String licensePlateNumberInput,
			@RequestParam String drivingLicenseNumberInput,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueDateFilter,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate pickupDateFilter) {

		System.out.println("licensePlateNumberInput: " + licensePlateNumberInput);

		ModelAndView modelAndView = new ModelAndView("AdminViewRentalTransactions");
		List<RentedCar> rentals = rentedCarDataMapper.getAllRentedCars();

		List<RentedCar> rentalsToSend = new ArrayList<>();

		for (RentedCar rental : rentals) {
			if (!StringUtils.isEmpty(licensePlateNumberInput)
					&& !licensePlateNumberInput.equals(rental.getCar().getLicensePlateNumber())) {
				continue;
			}

			if (!StringUtils.isEmpty(drivingLicenseNumberInput)
					&& !drivingLicenseNumberInput.equals(rental.getAssociatedClient().getDriverLicenceNumber())) {
				continue;
			}

			if (dueDateFilter != null && !dueDateFilter.equals(rental.getDueDate())) {
				continue;
			}

			if (pickupDateFilter != null && !pickupDateFilter.equals(rental.getStartDate())) {
				continue;
			}
			rentalsToSend.add(rental);

		}

		modelAndView.addObject("rentals", rentalsToSend);
		return modelAndView;

	}


	@RequestMapping(value = "/filterCancelledReturnedRecordsForAdmin", method = RequestMethod.POST)

	public ModelAndView filterCancelledReturnedRecordsForAdmin(@RequestParam String licensePlateNumberInput,
			@RequestParam String drivingLicenseNumberInput,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dueDateFilter,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate pickupDateFilter) {

		System.out.println("licensePlateNumberInput: " + licensePlateNumberInput);

		ModelAndView modelAndView = new ModelAndView("ReturnedAndCancelledTransactions");
		List<CancelledReturnedBooking> canRetBookings = crDataMapper.getAllRecords();

		List<CancelledReturnedBooking> toSend = new ArrayList<>();

		for (CancelledReturnedBooking canRet : canRetBookings) {
			if (!StringUtils.isEmpty(licensePlateNumberInput)
					&& !licensePlateNumberInput.equals(canRet.getCar().getLicensePlateNumber())) {
				continue;
			}

			if (!StringUtils.isEmpty(drivingLicenseNumberInput)
					&& !drivingLicenseNumberInput.equals(canRet.getAssociatedClient().getDriverLicenceNumber())) {
				continue;
			}

			if (dueDateFilter != null && !dueDateFilter.equals(canRet.getDueDate())) {
				continue;
			}

			if (pickupDateFilter != null && !pickupDateFilter.equals(canRet.getStartDate())) {
				continue;
			}
			toSend.add(canRet);

		}

		modelAndView.addObject("returnedCancelled", toSend);
		return modelAndView;

	}
	@RequestMapping("/showReturnedCancelledReservations")
	public ModelAndView showReturnedOrCancelledTransactions() {

		List<CancelledReturnedBooking> retCanbookings = crDataMapper.getAllRecords();

		return new ModelAndView("ReturnedAndCancelledTransactions", "returnedCancelled", retCanbookings);
	}

	@RequestMapping(value = "/backToAdminManageCatalog",  method = RequestMethod.POST)
	public ModelAndView backToAdminManageCatalog(Car car) {
		if(car.getAvailableReservedOrRented().equals("Available")) {
			car.setEditing(false);
		}
		else {
			car.setEditing(true);
		}
		carDataMapper.modifyCarRecord(car);
		return new ModelAndView("AdminCarCatalogPage", "cars", carDataMapper.getAllCars());

	}
	
	@RequestMapping("/backToAdminManageCatalogAfterTimeout")
	public ModelAndView backToAdminManageCatalogAfterTimeout() {
		if(editingCar.getAvailableReservedOrRented().equals("Available")) {
			editingCar.setEditing(false);
		}
		else {
			editingCar.setEditing(true);
		}
		carDataMapper.modifyCarRecord(editingCar);
		return new ModelAndView("AdminCarCatalogPage", "cars", carDataMapper.getAllCars());

	}

	@RequestMapping("/adminHomePage")
	public ModelAndView backToAdminHome() {

		return new ModelAndView("AdminHomePage");
	}

	@RequestMapping("/adminLogout")
	public ModelAndView adminLogout() {
		isAnyAdminLoggedIn = false;
		return new ModelAndView("main");

	}
}
