package com.team13.RentaRide.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.team13.RentaRide.model.Clerk;
import com.team13.RentaRide.tdgateway.ClerkTdGateway;
import com.team13.RentaRide.utils.DatabaseUtils;

public class ClerkDataMapper {
	ClerkTdGateway clerkgateway = new ClerkTdGateway();

	public boolean addClerkRecord(Clerk clerk) {
		try {
			HashMap<String, Object> hmClerk = new HashMap<>();

			hmClerk.put("EMAIL", clerk.getEmail());
			hmClerk.put("PASSWORD", clerk.getPassword());

			return clerkgateway.insertClerkRecord(hmClerk);
		} catch (Exception e) {
			System.out.println("Error while inserting a clerk record in the database");
			e.printStackTrace();
			return false;
		}
	}

	public List<Clerk> getAllClerkRecords() {

		ResultSet resultSet = clerkgateway.getAllClerkRecords();
		if (resultSet == null) {
			return new ArrayList<>();
		}

		List<Clerk> resClerk = new ArrayList<>();
		try {
			resClerk = parseResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return new ArrayList<>();
		}
		return resClerk;
	}

	private List<Clerk> parseResultSet(ResultSet resultSet) throws SQLException {

		List<Clerk> resClerk = new ArrayList<>();
		while (resultSet.next()) {
			Clerk user = getModelFromResultSet(resultSet);
			resClerk.add(user);
		}
		return resClerk;
	}

	private Clerk getModelFromResultSet(ResultSet resultSet) throws SQLException {
		
		return new Clerk(resultSet.getString(1), resultSet.getString(2));
	}

	public List<Clerk> getClerkByEmailPassword(String email, String password) {

		ResultSet result = clerkgateway.getClerkByEmailPassword(email, password);
		try {
			return parseResultSet(result);
		} catch (SQLException e) {
			System.out.println("Error while getting clerk record from db");
			e.printStackTrace();
			return null;
		}
	}
}
