package com.team13.RentaRide.model;

import java.time.LocalDate;
import java.util.Date;
/**
 * 
 * @author Admin
 *
 */
public class ReservedCar extends BookedCar {
	/**
	 * 
	 * @param id	
	 * @param car
	 * @param associatedClient
	 * @param startDate
	 * @param dueDate
	 * @param bookingTimestamp
	 */

	public ReservedCar(Integer id,Car car, Client associatedClient, LocalDate startDate, LocalDate dueDate, 
			Date bookingTimestamp) {
		super(id, car, associatedClient, startDate, dueDate, bookingTimestamp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ReservedCar [id=" + id + ", getBookingTimestamp()=" + getBookingTimestamp() + ", getCar()=" + getCar()
				+ ", getAssociatedClient()=" + getAssociatedClient() + ", getStartDate()=" + getStartDate()
				+ ", getDueDate()=" + getDueDate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
