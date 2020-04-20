package com.team13.RentaRide.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team13.RentaRide.mapper.CarDataMapper;
import com.team13.RentaRide.model.Car;

/**
 * Controller for Car
 * 
 * @author team 13
 *
 */

@Controller
public class CarDetailsController {
	/**
	 * The controller for car has different operations like:
	 * <li>Can browse detailed view of car</li>
	 * <li>check availability of car</li>
	 * <li>view the precious or next detailed view of car</li>
	 * <li>can go back to car catalog</li>
	 */
	public static List<Car> currentlySelectedCar = new ArrayList<Car>();
	CarDataMapper carDataMapper = new CarDataMapper();
	private static List<Car> cars = new ArrayList<Car>();
	private Car currentCar = null;
	
	@RequestMapping("/backfromRental")
	public ModelAndView backFromRentalPage( @RequestParam String CarLicenseNo) {

		Car car = carDataMapper.getCarByLicenseNumber(CarLicenseNo);
		car.setEditing(false);
		carDataMapper.modifyCarRecord(car);
		
		Car c = carDataMapper.getCarByLicenseNumber(CarLicenseNo);
		return showCarView(c);

	}
	
	@RequestMapping("/backfromReservation")
	public ModelAndView backFromReservation( @RequestParam String CarLicenseNo) {
		Car car = carDataMapper.getCarByLicenseNumber(CarLicenseNo);
		car.setEditing(false);
		carDataMapper.modifyCarRecord(car);

		Car c = carDataMapper.getCarByLicenseNumber(CarLicenseNo);
		return showCarView(c);
	}
	
	

	@RequestMapping("/carDetailView")
	public ModelAndView showCarDetails(@RequestParam String licensePlateInput) {
		cars = carDataMapper.getAllCars();
		
		currentCar = carDataMapper.getCarByLicenseNumber(licensePlateInput);
		return showCarView(currentCar);
	}

	@RequestMapping("/back")
	public ModelAndView showPreviousCar() {

		Integer index = getIndexOfCar(currentCar, cars);
		System.out.println("checking index and size " + index + ", " + cars.size());

		if (index != null && index != 0) {
			Car prevCar = cars.get((index - 1));
			currentCar = prevCar;
			return showCarView(prevCar);
		} else {
			Car sameCar = currentCar;
			return showCarView(sameCar);
		}
	}

	private Integer getIndexOfCar(Car paramCar, List<Car> carList) {

		int index = 0;
		for (Car car : carList) {
			if (car.getLicensePlateNumber().equals(paramCar.getLicensePlateNumber())) {
				return index;
			}
			index++;
		}
		return null;
	}

	@RequestMapping("/next")
	public ModelAndView showNextCar() {
		Integer index = getIndexOfCar(currentCar, cars);
		System.out.println("checking index and size " + index + ", " + cars.size());
		if (index != null && index + 1 != cars.size()) {
			Car nextCar = cars.get((index + 1));
			currentCar = nextCar;
			return showCarView(nextCar);
		} else {
			Car sameCar = currentCar;
			return showCarView(sameCar);
		}

	}

	@RequestMapping("/backtoCarCatalog")
	public ModelAndView showCarCatalogPage() {
		if(!currentlySelectedCar.isEmpty()) {
			currentlySelectedCar.clear();
		}
		ModelAndView modelAndView = new ModelAndView("CarCatalog");
		modelAndView.addObject("cars", carDataMapper.getAllCars());
		return modelAndView;
	}

	
	
	@RequestMapping("/refreshCarDetails") 
	public ModelAndView refreshCarDetails() {
		
	
		return showCarView(currentCar);
		
		
		
	}
	
	
	
	
	
	
	/**
	 * @param car car object is passed to check the availability of the car
	 * @return model and view of car.
	 */

	public ModelAndView showCarView(Car car) {
		
		Car c = carDataMapper.getCarByLicenseNumber(car.getLicensePlateNumber());
		ModelAndView modelAndView = new ModelAndView("CarDetails", "car", c);
		
		
		if(! currentlySelectedCar.isEmpty()) {
			currentlySelectedCar.clear();
		}
		currentlySelectedCar.add(car);
		
		
		if (c.isEditing()) {
			modelAndView.addObject("canReserveOrNot", "Unavaialable for Reserving");
			modelAndView.addObject("canRentOrNot", "Unavaialable for Renting");
			modelAndView.addObject("disableOrNo", "disabled");
		}
		else if (c.getAvailableReservedOrRented().equals("Reserved")) {
			modelAndView.addObject("canReserveOrNot", "Already Reserved");
			modelAndView.addObject("canRentOrNot", "Cannot Rent Now");
			modelAndView.addObject("disableOrNo", "disabled");
		} 
		else if (c.getAvailableReservedOrRented().equals("Available")) {
			modelAndView.addObject("canReserveOrNot", "Reserve Now");
			modelAndView.addObject("canRentOrNot", "Rent Now");
		}	
		else if (c.getAvailableReservedOrRented().equals("Rented")){
			modelAndView.addObject("canReserveOrNot", "Cannot Reserve Now");
			modelAndView.addObject("canRentOrNot", "Already Rented");
			modelAndView.addObject("disableOrNo", "disabled");
		}
		
		
		return modelAndView;
	}

}
