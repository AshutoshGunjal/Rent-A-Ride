package com.team13.RentaRide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.team13.RentaRide.tdgateway.DatabaseCreator;

/**
 * The system initiates the spring boot application and database from this
 * class.
 * 
 * @author Team 13
 *
 */

@SpringBootApplication
public class RentARideApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentARideApplication.class, args);
		DatabaseCreator.testDb();
	}
}
