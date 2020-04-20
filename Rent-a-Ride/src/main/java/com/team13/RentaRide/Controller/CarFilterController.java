package com.team13.RentaRide.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team13.RentaRide.mapper.CarDataMapper;
import com.team13.RentaRide.model.Car;

/**
 * Car filter Controller
 * @author Team 13
 *
 */

@Controller
public class CarFilterController {
	private CarDataMapper carDataMapper = new CarDataMapper();

	/**
	 * @param modelInput model of the car filtered to search the specified car.
	 * @param typeInput type of the car filtered to search the specified car.
	 * @param makeInput make of the car filtered to search the specified car.
	 * @param colorInput color of the car filtered to search the specified car.
	 * @param yearInput year of the car filtered to search the specified car.
	 * @param yearOffset year offset of the car filtered to search the specified car.
	 * @param availabilityInput availability of the car filtered to search the specified car.
	 * @return model and view after successful filteration.
	 */

	@RequestMapping(value = "/filterCarsForClerk", method = RequestMethod.POST)
	public ModelAndView filterCarsForClerk(@RequestParam String modelInput, @RequestParam String typeInput,
			@RequestParam String makeInput, @RequestParam String colorInput, @RequestParam String yearInput,
			@RequestParam Integer yearOffset, @RequestParam String availabilityInput) {
		List<Car> carsToSend = filterCars(modelInput, typeInput, makeInput, colorInput, yearInput, yearOffset,
				availabilityInput);
		return new ModelAndView("CarCatalog", "cars", carsToSend);

	}

	@RequestMapping(value = "/filterCarsForAdmin", method = RequestMethod.POST)
	public ModelAndView filterCarsForAdmin(@RequestParam String modelInput, @RequestParam String typeInput,
			@RequestParam String makeInput, @RequestParam String colorInput, @RequestParam String yearInput,
			@RequestParam Integer yearOffset, @RequestParam String availabilityInput) {

		List<Car> carsToSend = filterCars(modelInput, typeInput, makeInput, colorInput, yearInput, yearOffset,
				availabilityInput);

		return new ModelAndView("AdminCarCatalogPage", "cars", carsToSend);

	}
	/**
	 * @param modelInput model of the car filtered to search the specified car.
	 * @param typeInput type of the car filtered to search the specified car.
	 * @param makeInput make of the car filtered to search the specified car.
	 * @param colorInput color of the car filtered to search the specified car.
	 * @param yearInput year of the car filtered to search the specified car.
	 * @param yearOffset year offset of the car filtered to search the specified car.
	 * @param availabilityInput availability of the car filtered to search the specified car.
	 * @return cars to send after successful filteration.
	 */
	private List<Car> filterCars(String modelInput, String typeInput, String makeInput, String colorInput,
			String yearInput, Integer yearOffset, String availabilityInput) {
		List<Car> cars = carDataMapper.getAllCars();
		List<Car> carsToSend = new ArrayList<>();
		Integer yearFilter = !StringUtils.isEmpty(yearInput) ? Integer.valueOf(yearInput) : 0;

		for (Car car : cars) {
			if (!StringUtils.isEmpty(modelInput) && !modelInput.equals(car.getModel())) {
				continue;
			}

			if (!StringUtils.isEmpty(typeInput) && !typeInput.equals(car.getType())) {
				continue;
			}

			if (!StringUtils.isEmpty(makeInput) && !makeInput.equals(car.getMake())) {
				continue;
			}

			if (!StringUtils.isEmpty(colorInput) && !colorInput.equals(car.getColor())) {
				continue;
			}

			if (!StringUtils.isEmpty(availabilityInput)
					&& !availabilityInput.equals(car.getAvailableReservedOrRented())) {
				continue;
			}

			if (yearOffset == null && !StringUtils.isEmpty(yearInput)
					&& !Integer.valueOf(yearInput).equals(car.getYear())) {
				continue;

			} else if (yearOffset != null && yearOffset < 0) {
				if (!(car.getYear() >= yearFilter + yearOffset && car.getYear() <= yearFilter)) {
					continue;
				}
			} else if (yearOffset != null && yearOffset > 0) {
				if (!(car.getYear() <= yearFilter + yearOffset && car.getYear() >= yearFilter)) {
					continue;
				}
			}

			carsToSend.add(car);
		}
		return carsToSend;
	}

}
