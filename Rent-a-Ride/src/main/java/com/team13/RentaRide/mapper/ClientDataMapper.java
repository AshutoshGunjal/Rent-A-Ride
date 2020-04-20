package com.team13.RentaRide.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.team13.RentaRide.model.Client;
import com.team13.RentaRide.tdgateway.ClientTdGateway;
import com.team13.RentaRide.utils.DatabaseUtils;

public class ClientDataMapper {
	ClientTdGateway clientDataGateway = new ClientTdGateway();

	public boolean addClientRecord(Client client) {
		try {
			HashMap<String, Object> client_parameterMap = new HashMap<>();

			client_parameterMap.put("CLIENT_ID", client.getId());
			client_parameterMap.put("DRIVER_LICENCE_NUMBER", client.getDriverLicenceNumber());
			client_parameterMap.put("CLIENT_FIRST_NAME", client.getClientFirstName());
			client_parameterMap.put("CLIENT_LAST_NAME", client.getClientLastName());
			client_parameterMap.put("CLIENT_PHONE_NUMBER", client.getPhoneNumber());
			client_parameterMap.put("CLIENT_LICENCE_EXPIRY_DATE", client.getLicenceExpiryDate());
			client_parameterMap.put("EDITING", client.isEditing());
		
			return clientDataGateway.insertClientRecord(client_parameterMap);
			
		} catch (Exception e) {
			System.out.println("Error while inserting a client record in the database");
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteClientRecord(String driverLicenceNumber) {
		try {
			return clientDataGateway.deleteClient(driverLicenceNumber);

		} catch (Exception e) {
			System.out.println("Error while deleting a client record in the database");
			e.printStackTrace();
			return false;
		}
	}

	public boolean modifyClientRecord(Client client) {
		try {
			HashMap<String, Object> client_parameterMap = new HashMap<>();

			client_parameterMap.put("CLIENT_ID", client.getId());
			client_parameterMap.put("DRIVER_LICENCE_NUMBER", client.getDriverLicenceNumber());
			client_parameterMap.put("CLIENT_FIRST_NAME", client.getClientFirstName());
			client_parameterMap.put("CLIENT_LAST_NAME", client.getClientLastName());
			client_parameterMap.put("CLIENT_PHONE_NUMBER", client.getPhoneNumber());
			client_parameterMap.put("CLIENT_LICENCE_EXPIRY_DATE", client.getLicenceExpiryDate());
			client_parameterMap.put("EDITING", client.isEditing());
			
			return clientDataGateway.modifyClient(client_parameterMap);
		} catch (Exception e) {
			System.out.println("Error while modifying a client record in the database");
			e.printStackTrace();
			return false;
		}

	}

	public List<Client> getAllClients() {

		ResultSet resultSet = clientDataGateway.getAllClients();
		if (resultSet == null) {

			return new ArrayList<>();
		}

		List<Client> clients = new ArrayList<>();
		try {
			clients = parseClientResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return new ArrayList<>();
		}

		return clients;

	}

	private List<Client> parseClientResultSet(ResultSet resultSet) throws SQLException {
		List<Client> clients = new ArrayList<>();
		while (resultSet.next()) {
			Client client = getModelFromResultSet(resultSet);
			clients.add(client);
		}
		return clients;
	}

	private Client getModelFromResultSet(ResultSet resultSet) throws SQLException {
		Client client = new Client();
		client.setId(resultSet.getInt(1));
		client.setDriverLicenceNumber(resultSet.getString(2));
		client.setClientFirstName(resultSet.getString(3));
		client.setClientLastName(resultSet.getString(4));
		client.setPhoneNumber(resultSet.getString(5));
		client.setLicenceExpiryDate(resultSet.getDate(6).toLocalDate());
		client.setEditing(resultSet.getBoolean(7));
		
		return client;
	}

	public Client getClientByDrivingLicense(String driverLicenceNumber) {

		ResultSet result = clientDataGateway.getClientByDrivingLicense(driverLicenceNumber);
		if (result == null) {
			return null;
		}
		Client client = null;
		try {
			if (!result.next()) {
				return null;
			}
			client = getModelFromResultSet(result);
		} catch (SQLException e) {
			System.out.println("Error while getting client record from database");
			e.printStackTrace();
			return null;
		}
		return client;
	}

}
