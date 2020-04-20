<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
<link href="styles/universal.css" rel="stylesheet" type="text/css"/>
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
<script type="text/javascript" src="scripts/admin-car-catalog-filter.js"></script>  
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form>

<div class="col-md-6">
	<h2>Reservation Transactions</h2> 
	</div>
	<div class="col-md-6">
		<h3>
			<a href="/adminManageCatalog"><u>Car Catalog</u></a>
			&nbsp; &nbsp;
			<a href="/backToAdminRentedCarList"><u>Rented Transactions</u></a>
			&nbsp; &nbsp;
			<a href="/adminLogout" ><u>Logout</u></a>
		</h3>
	</div>


<div class="container">

	<label>License Plate Number </label> 
			<input type="hidden" name="licensePlateNumberInput" id="licensePlateNumberInput"/>
			<select id="licensePlateNumber" onChange="setInputValue('licensePlateNumber')" >
				<option value="invalid">---</option>
				
				<c:forEach var="reservedCar" items="${reservations}" varStatus="loop">
					<option value="${reservedCar.car.licensePlateNumber}">${reservedCar.car.licensePlateNumber}</option>
				</c:forEach>
			</select>
			
	&nbsp; &nbsp;
	<label>Driving License Number</label> 
			<input type="hidden" name="drivingLicenseNumberInput" id="drivingLicenseNumberInput"/>
			<select id="drivingLicense" onChange="setInputValue('drivingLicenseNumber')" >
				<option value="invalid">---</option>
				<c:forEach var="reservedCar" items="${reservations}" varStatus="loop">
					<option value="${reservedCar.associatedClient.driverLicenceNumber}">${reservedCar.associatedClient.driverLicenceNumber}</option>
				</c:forEach>
			</select>
			
	&nbsp; &nbsp;
	<label>Pickup Date</label> 
	<input type="date" name="pickupDateFilter" id="pickupDateFilter"/>
	
	&nbsp; &nbsp;
	<label>Drop-off Date</label> 
	<input type="date" name="dueDateFilter" id="dueDateFilter"/>
	
	&nbsp; 
		<button type="submit" formaction="/filterReservationRecordsForAdmin" formmethod="post">
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
		
		<c:forEach var="reservedCar" items="${reservations}" varStatus="loopCounter">
		<tr class="CarInfo">
			<td>${reservedCar.car.licensePlateNumber}</td>
			<td>${reservedCar.associatedClient.clientFirstName}</td>
			<td>${reservedCar.associatedClient.clientLastName}</td>
			<td>${reservedCar.associatedClient.driverLicenceNumber}</td>
			<td>${reservedCar.startDate}</td>
			<td>${reservedCar.dueDate}</td>
			<td>${reservedCar.bookingTimestamp}</td>
		</tr>
		</c:forEach>
		
	</table>
	</div>
	</div>
</form>

</body>
</html>