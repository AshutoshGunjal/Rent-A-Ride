<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
<link href="styles/universal.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="scripts/admin-car-catalog-filter.js"></script>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
<meta charset="ISO-8859-1">
<title>View Rentals for Admin</title>
</head>

<body>
<form>
<div class="container">
<div class="col-md-6">
	<h2>Rental Transactions</h2> 
	</div>
	<div class="col-md-6">
		<h3>
			<a href="/adminManageCatalog" ><u>View Car Catalog</u></a>
			&nbsp; &nbsp;
			<a href="/backToAdminReservedCarList"><u>View Reserved Transactions</u></a>
			&nbsp; &nbsp;
			<a href="/adminLogout" ><u>Logout</u></a>
		</h3>
	</div> 

	<label>License Plate Number </label> 
			<input type="hidden" name="licensePlateNumberInput" id="licensePlateNumberInput"/>
			<select id="licensePlateNumber" onChange="setInputValue('licensePlateNumber')" >
				<option value="invalid">---</option>
				
				<c:forEach var="rentedCar" items="${rentals}" varStatus="loop">
					<option value="${rentedCar.car.licensePlateNumber}">${rentedCar.car.licensePlateNumber}</option>
				</c:forEach>
			</select>
			
	&nbsp; &nbsp;
	<label>Driving License Number</label> 
			<input type="hidden" name="drivingLicenseNumberInput" id="drivingLicenseNumberInput"/>
			<select id="drivingLicense" onChange="setInputValue('drivingLicenseNumber')" >
				<option value="invalid">---</option>
				<c:forEach var="rentedCar" items="${rentals}" varStatus="loop">
					<option value="${rentedCar.associatedClient.driverLicenceNumber}">${rentedCar.associatedClient.driverLicenceNumber}</option>
				</c:forEach>
			</select>
			
	&nbsp; &nbsp;
	<label>Pickup Date</label> 
	<input type="date" name="pickupDateFilter" id="pickupDateFilter"/>
	
	&nbsp; &nbsp;
	<label>Drop-off Date</label> 
	<input type="date" name="dueDateFilter" id="dueDateFilter"/>
	
	&nbsp; 
		<button type="submit" formaction="/filterRentalRecordsForAdmin" formmethod="post">
			<i class="fa fa-search text-green" aria-hidden="true"></i>
		</button>
	
			
<hr style="clear:both;"/>
<div class="container">
	<table id="myTable" class="sortable">
		<tr class="header">
			<th style="width: 10%;">License Plate Number</th>
			<th style="width: 5%;">First Name</th>
			<th style="width: 5%;">Last Name</th>
			<th style="width: 20%;">Driving License Number</th>
			<th style="width: 10%;">Pick-Up Date</th>
			<th style="width: 10%;">Drop-Off Date</th>
			<th style="width: 20%;">Booking Date-time</th>
		</tr>
		
		<c:forEach var="rentedCar" items="${rentals}" varStatus="loopCounter">
		<tr class="CarInfo">
			<td>${rentedCar.car.licensePlateNumber}</td>
			<td>${rentedCar.associatedClient.clientFirstName}</td>
			<td>${rentedCar.associatedClient.clientLastName}</td>
			<td>${rentedCar.associatedClient.driverLicenceNumber}</td>
			<td>${rentedCar.startDate}</td>
			<td>${rentedCar.dueDate}</td>
			<td>${rentedCar.bookingTimestamp}</td>
		</tr>
		</c:forEach>
		
	</table>
	</div>
</div>
</form>
</body>
</html>