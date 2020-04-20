package com.team13.RentaRide.model;
/**
 * 
 * @author Admin
 *
 */

public class User {
	protected Integer id;
	private String email;
	private String password;

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [email=" + getEmail() + ", password=" + getPassword() + "]";
	}

}
