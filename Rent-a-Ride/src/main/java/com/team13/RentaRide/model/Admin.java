package com.team13.RentaRide.model;

public class Admin extends User{
	/**
	 * <p>Admin needs to enter the below mentioned credentials in-order to use the system.The system will carry out the authentication process before successful login</p>
	 * @param email admin credential email Id to use the system.
	 * @param password admin credential password to use the system.
	 * @author team 13
	 */

	public Admin(String email, String password) {
		super(email, password);
	}

		
	}
