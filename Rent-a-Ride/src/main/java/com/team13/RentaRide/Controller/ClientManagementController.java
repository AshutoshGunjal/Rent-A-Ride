package com.team13.RentaRide.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.team13.RentaRide.mapper.ClientDataMapper;
import com.team13.RentaRide.model.Client;

/**
 * 
 * @author Admin
 *
 */

@Controller

public class ClientManagementController {

	private ClientDataMapper clientDataMapper = new ClientDataMapper();
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/createNewClient")
	public ModelAndView createClientRecord() {

		ModelAndView modelAndView = new ModelAndView("CreateNewClientRecord");

		return modelAndView;
	}

	/**
	 * 
	 * @param driverLicenceNumber
	 * @param clientFirstName
	 * @param clientLastName
	 * @param phoneNumber
	 * @param licenceExpiryDate
	 * @return
	 */
	@RequestMapping("/goToClientManagementPageAfterCreation")
	public ModelAndView confirmCreationOfClient(@RequestParam String driverLicenceNumber,
			@RequestParam String clientFirstName, @RequestParam String clientLastName, @RequestParam String phoneNumber,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate licenceExpiryDate) {
		Client client = new Client();
		client.setClientFirstName(clientFirstName);
		client.setClientLastName(clientLastName);
		client.setDriverLicenceNumber(driverLicenceNumber);
		client.setLicenceExpiryDate(licenceExpiryDate);
		client.setPhoneNumber(phoneNumber);
		client.setEditing(false);
		
		clientDataMapper.addClientRecord(client);

		ModelAndView modelAndView = new ModelAndView("ClientManagementPage", "clients",
				clientDataMapper.getAllClients());

		return modelAndView;
	}

	/**
	 * 
	 * @param driverLicenceNumberForModify
	 * @return
	 */
	@RequestMapping("/gotoModifyClientRecord")
	public ModelAndView showClientModificationPage(@RequestParam String driverLicenceNumberForModify) {
	
		ModelAndView modelAndView = new ModelAndView("ModifyClientRecord");
	Client client = clientDataMapper.getClientByDrivingLicense(driverLicenceNumberForModify);
		if(! client.isEditing()) {
			client.setEditing(true);
			clientDataMapper.modifyClientRecord(client);
			modelAndView.addObject("client",client);
			return modelAndView;
		}
		else {
			modelAndView.addObject("client",client);
			modelAndView.addObject("disableOrNot", "disabled");
			modelAndView.addObject("readOnly", "readonly");
			return modelAndView;
			
		}
		
	}
	
	@RequestMapping("/backToClientManagementPage")
	public ModelAndView cancelModifyingClient(@RequestParam String driverLicenseNumber) { 
		Client client = clientDataMapper.getClientByDrivingLicense(driverLicenseNumber);
		client.setEditing(false);
		clientDataMapper.modifyClientRecord(client);
		
		List<Client> clients = clientDataMapper.getAllClients();
		ModelAndView modelAndView = new ModelAndView("ClientManagementPage");
		modelAndView.addObject("clients", clients);
		
		return modelAndView;
	}

	/**
	 * 
	 * @param driverLicenceNumberForDelete
	 * @return
	 */
	@RequestMapping("/gotoDeleteClientRecord")
	public ModelAndView deleteClientRecord(@RequestParam String driverLicenceNumberForDelete) {
		ModelAndView modelAndView = new ModelAndView("ClientManagementPage");
		
		Client client = clientDataMapper.getClientByDrivingLicense(driverLicenceNumberForDelete);
		List<Client> clients = clientDataMapper.getAllClients();
		
		if(client.isEditing()) {
			modelAndView.addObject("errorMessage", "CANNOT DELETE THIS CLIENT!!");
			modelAndView.addObject("clients",clients);
			return modelAndView;
		}
		else {
			clientDataMapper.deleteClientRecord(driverLicenceNumberForDelete);
			
			List<Client> newClients = clientDataMapper.getAllClients();
			modelAndView.addObject("clients",newClients);
			
			return modelAndView;
		}
		
	}

	/**
	 * 
	 * @param driverLicenseNumber
	 * @param clientFirstName
	 * @param clientLastName
	 * @param phoneNumber
	 * @param licenceExpiryDate
	 * @return
	 */
	@RequestMapping(value = "/gotoClientManagementPageAfterModification", method = RequestMethod.POST)
	public ModelAndView confirmClientRecordModify(@RequestParam String driverLicenseNumberInput,
			@RequestParam String clientFirstName, @RequestParam String clientLastName, @RequestParam String phoneNumber,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate licenceExpiryDate) {

		Client client = new Client();
		client.setClientFirstName(clientFirstName);
		client.setClientLastName(clientLastName);
		client.setDriverLicenceNumber(driverLicenseNumberInput);
		client.setLicenceExpiryDate(licenceExpiryDate);
		client.setPhoneNumber(phoneNumber);
		client.setEditing(false);
		
		clientDataMapper.modifyClientRecord(client);

		List<Client> clients = clientDataMapper.getAllClients();
		
		ModelAndView modelAndView = new ModelAndView("ClientManagementPage", "clients",clients);

		return modelAndView;
	}

}
