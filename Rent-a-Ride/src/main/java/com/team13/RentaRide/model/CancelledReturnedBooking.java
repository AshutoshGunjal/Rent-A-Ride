package com.team13.RentaRide.model;

import java.time.LocalDate;
import java.util.Date;

public class CancelledReturnedBooking extends BookedCar {

	
	private Date cancelReturnTimeStamp;
	
	public Date getCancelReturnTimeStamp() {
		return cancelReturnTimeStamp;
	}

	public void setCancelReturnTimeStamp(Date cancelReturnTimeStamp) {
		this.cancelReturnTimeStamp = cancelReturnTimeStamp;
	}

	public CancelledReturnedBooking(Integer id, Car car, Client associatedClient, LocalDate startDate,
			LocalDate dueDate, Date bookingTimestamp, Date cancelReturnTimeStamp) {
		
		super(id, car, associatedClient, startDate, dueDate, bookingTimestamp);
		this.cancelReturnTimeStamp = cancelReturnTimeStamp;
		
	}
	
	

}
