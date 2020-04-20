package com.team13.RentaRide.tdgateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.team13.RentaRide.utils.DatabaseUtils;
import com.team13.RentaRide.utils.PropertyFileReaderUtils;
/**
 * 
 * @author Admin
 *
 */

public class DatabaseCreator {

	public static void testDb() {
		/**
		 * 
		 */
		
		Properties dbProperties = PropertyFileReaderUtils.getProperties();

		String user = dbProperties.getProperty("db.username");
		String password = dbProperties.getProperty("db.password");
		String schemaName = dbProperties.getProperty("db.schema");
//		String url = "jdbc:mysql://localhost:3306/" + schemaName + "?useSSL=false";
		String urlShort = "jdbc:mysql://localhost/";

		String createDb = "CREATE DATABASE IF NOT EXISTS RentARideDatabase";
		
		String useDb = "USE " + schemaName + ";";

		String createTableAdmin = "CREATE TABLE IF NOT EXISTS  Admin       (id int NOT NULL auto_increment, email varchar(255) UNIQUE, password varchar(255) UNIQUE, PRIMARY KEY (id) );";

		String createTableClerk = "CREATE TABLE IF NOT EXISTS  Clerk       (id int NOT NULL auto_increment, email varchar(255) UNIQUE, password varchar(255) UNIQUE, PRIMARY KEY (id) );";

		String createTableCar = "CREATE TABLE IF NOT EXISTS  Car         (id int NOT NULL auto_increment, license_plate_number varchar(30) UNIQUE, make varchar(20), model varchar(20), type varchar(20), color varchar(20), year int(4), description varchar(255), price DECIMAL(5,2), available_reserved_or_rented varchar(20), editing BOOLEAN, PRIMARY KEY (id));";

		String createTableClient = "CREATE TABLE IF NOT EXISTS  Client      (id int NOT NULL auto_increment, driver_licence_number varchar(30) UNIQUE, client_first_name varchar(30), client_last_name varchar(30), phone_number varchar(10), licence_expiry_date Date , editing BOOLEAN, PRIMARY KEY (id) );";

		String createTableReservedCar = "CREATE TABLE IF NOT EXISTS  ReservedCar (id int NOT NULL auto_increment, car_id int, client_id int, start_date Date, due_date Date, booking_timestamp TIMESTAMP , PRIMARY KEY (id), FOREIGN KEY (car_id) REFERENCES Car(id), FOREIGN KEY (client_id) REFERENCES Client(id) ); ";

		String createTableRentedCar = "CREATE TABLE IF NOT EXISTS  RentedCar   (id int NOT NULL auto_increment, car_id int, client_id int, start_date Date, due_date Date, booking_timestamp TIMESTAMP , PRIMARY KEY (id), FOREIGN KEY (car_id) REFERENCES Car(id), FOREIGN KEY (client_id) REFERENCES Client(id) ); ";

		String createTableCancelledReturnedTransactions = "CREATE TABLE IF NOT EXISTS cancelledReturned ( id int NOT NULL auto_increment, car_id int, client_id int, start_date Date, due_date Date, booking_timestamp TIMESTAMP, cancelled_returned_timestamp TIMESTAMP, PRIMARY KEY (id) );" ; 
		
	
		try {

			DatabaseUtils.initConnection(urlShort, user, password);
			Connection con = DatabaseUtils.getDbConnection();

			Statement st1 = con.createStatement();
			st1.execute(createDb);

			Statement st2 = con.createStatement();
			st2.execute(useDb);

			Statement st3 = con.createStatement();
			st3.execute(createTableAdmin);

			Statement st4 = con.createStatement();
			st4.execute(createTableClerk);

			Statement st5 = con.createStatement();
			st5.execute(createTableCar);

			Statement st6 = con.createStatement();
			st6.execute(createTableClient);

			Statement st7 = con.createStatement();
			st7.execute(createTableRentedCar);

			Statement st8 = con.createStatement();
			st8.execute(createTableReservedCar);
			
			Statement st9 = con.createStatement();
			st9.execute(createTableCancelledReturnedTransactions);
		

			System.out.println("Tables created");

		} catch (SQLException ex) {
			System.out.println(ex);
		}
	}
}
