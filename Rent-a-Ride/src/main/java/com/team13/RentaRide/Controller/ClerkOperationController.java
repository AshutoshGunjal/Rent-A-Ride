package com.team13.RentaRide.Controller;

import java.util.List;
import java.util.Stack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team13.RentaRide.mapper.CarDataMapper;
import com.team13.RentaRide.mapper.ClerkDataMapper;
import com.team13.RentaRide.mapper.ClientDataMapper;
import com.team13.RentaRide.model.Car;
import com.team13.RentaRide.model.Clerk;

/**
 * 
 * @author Admin
 *
 */
@Controller
public class ClerkOperationController {
	/**
	 * 
	 * @return
	 */
	public static Stack<Clerk> clerksLoggedIn = new Stack();
	private ClerkDataMapper clerkDataMapper = new ClerkDataMapper();
	private CarDataMapper carDataMapper = new CarDataMapper();
	private ClientDataMapper clientDataMapper = new ClientDataMapper();

	@RequestMapping("/tryToRegisterAsClerk")
	public ModelAndView showRegisterClerkPage() {
		return new ModelAndView("ClerkRegisterPage");

	}

	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 */

	@RequestMapping(value = "/clerkRegistered", method = RequestMethod.POST)
	public ModelAndView registerClerk(@RequestParam String email, @RequestParam String password) {

		Clerk clerk = new Clerk(email, password);
		clerkDataMapper.addClerkRecord(clerk);

		return new ModelAndView("ClerkLoginPage");
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/viewCarCatalog")
	public ModelAndView showCarCatalogPage() {

		ModelAndView modelAndView = new ModelAndView("CarCatalog");
		List<Car> allCars = carDataMapper.getAllCars();
		modelAndView.addObject("cars", allCars);
		return modelAndView;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/showClientManagement")
	public ModelAndView showClientManagementPage() {

		ModelAndView modelAndView = new ModelAndView("ClientManagementPage");
		modelAndView.addObject("clients", clientDataMapper.getAllClients());
		return modelAndView;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/ClerkLoginPage")
	public ModelAndView showLoginPage() {
		return new ModelAndView("ClerkLoginPage");
	}

	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/tryTologinAsClerk", method = RequestMethod.POST)

	public ModelAndView showWelcomePage(@RequestParam String email, @RequestParam String password) {
		List<Clerk> clerk = clerkDataMapper.getClerkByEmailPassword(email, password);


		if (!clerk.isEmpty()) {
			clerksLoggedIn.push(clerk.get(0));
			ModelAndView modelAndView = new ModelAndView("ClerkHomePage");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("ClerkLoginPage");
		modelAndView.addObject("errorMessage", "Invalid username or password. Please try again.");
		return modelAndView;

	}


	@RequestMapping("/clerkLogout")
	public ModelAndView clerkLogout() {
		Stack<Clerk> clerksIn = clerksLoggedIn;
		if(clerksIn.size()>0) {
			clerksLoggedIn.pop();
		}
		Stack<Clerk> clerksIn2 = clerksLoggedIn;

		return new ModelAndView("main");

	}

	@RequestMapping("/clerkBackToHome") 
	public ModelAndView clerkBackHome() {

		return new ModelAndView("ClerkHomePage");
	}



}
