package com.team13.RentaRide.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.team13.RentaRide.model.Admin;
import com.team13.RentaRide.tdgateway.AdminTdGateway;
import com.team13.RentaRide.utils.DatabaseUtils;

public class AdminDataMapper {
	private AdminTdGateway gtway = new AdminTdGateway();

	public boolean addAdminRecord(Admin admin) {
		try {
			HashMap<String, String> map = new HashMap<>();
			map.put("email", admin.getEmail());
			map.put("password", admin.getPassword());
			return gtway.insertAdminRecord(map);
		} catch (Exception e) {
			System.out.println("Error while inserting a admin record in the database");
			e.printStackTrace();
			return false;
		}
	}

	public List<Admin> getAllAdminRecords() {

		ResultSet resultSet = gtway.getAllAdminRecords();
		if (resultSet == null) {
			return new ArrayList<>();
		}

		List<Admin> admins = new ArrayList<>();
		try {
			admins = parseResultSet(resultSet);
		} catch (SQLException e) {
			System.out.println(DatabaseUtils.QUERY_EXECUTION_ERROR_MESSAGE);
			e.printStackTrace();
			return new ArrayList<>();
		}

		return admins;

	}

	private List<Admin> parseResultSet(ResultSet resultSet) throws SQLException {
		List<Admin> admins = new ArrayList<>();
		while (resultSet.next()) {
			Admin admin = new Admin(resultSet.getString(2), resultSet.getString(3));
			admins.add(admin);
		}

		return admins;
	}

	
	
	
	
}
