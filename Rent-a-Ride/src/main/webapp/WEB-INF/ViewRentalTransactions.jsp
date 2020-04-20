<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
<link href="styles/universal.css" rel="stylesheet" type="text/css"/>
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>

<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>



<div class="col-md-6">
	<h2>Rental Transactions</h2> 
	</div>
	<div class="col-md-6">
		<h3>
			<a href="/backToCarCatalog"><u>Car Catalog</u></a>
			&nbsp; &nbsp;
			<a href="/backToReservedCarList"><u>Reserved Transaction</u></a>
		</h3>
	</div> 

<hr style="clear:both;"/>
<div class="container">
	<table id="myTable" class="sortable">
		<tr class="header">
			<th style="width: 20%;">License Plate Number</th>
			<th style="width: 5%;">First Name</th>
			<th style="width: 5%;">Last Name</th>
			<th style="width: 20%;">Driving License Number</th>
			<th style="width: 10%;">Pick-Up Date</th>
			<th style="width: 10%;">Drop-Off Date</th>
			<th style="width: 20%;">Booking Date-time</th>
			<th style="width: 10%;">Operation</th>
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
			
			<td>  
			<form method ="post">
			<input value= "${rentedCar.car.licensePlateNumber}" type = "hidden" name = "carLicensePlateNumber" />
			<input type = "submit" formaction="/handleTheReturnThisRental" value="RETURN">
			</form>
			</td>
		</tr>
		</c:forEach>
		
	</table>
	</div>

</body>
</html>