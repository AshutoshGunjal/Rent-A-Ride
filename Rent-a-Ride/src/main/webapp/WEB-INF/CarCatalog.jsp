<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<meta name="viewport" content="width=device-width, initial-scale=1">
	
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/custom.css" rel="stylesheet" />
<script type="text/javascript" src="scripts/car-catalog-script.js"></script>  
<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>

</head>
<body>
<form method="POST" action="filterCarsForClerk">
<div class="container">
	<div align="right">
	<div class="col-md-6">
	<h2>CAR CATALOG</h2> 
	</div>
	<div class="col-md-6">
		<h3>
			<a href="/backToRentedCarList"><u>See All Rentals</u></a>
			<a href="/backToReservedCarList"><u>See All Reservations</u></a>
		</h3>
	</div> 
	</div>

	<hr style="clear:both;"/>
	<div>
		<label>Model</label> 
		<input type="hidden" name="modelInput" id="modelInput"/>
		<select id="model" onChange="setInputValue('model')" >
			<option value="invalid">---</option>
			<c:forEach var="car" items="${cars}" varStatus="loop">
				<option value="${car.model}">${car.model}</option>
			</c:forEach>
		</select>
		&nbsp;<label>Type</label>
		<input type="hidden" name="typeInput" id="typeInput"/>
		<select id="type" onChange="setInputValue('type')">
			<option value="invalid">---</option>
			<c:forEach var="car" items="${cars}" varStatus="loop">
				<option value="${car.type}">${car.type}</option>
			</c:forEach>
		</select> 
		&nbsp;<label>Make</label> 
		<input type="hidden" name="makeInput" id="makeInput"/>
		<select id="make" onChange="setInputValue('make')">
			<option value="invalid">---</option>
			<c:forEach var="car" items="${cars}" varStatus="loop">
				<option value="${car.make}">${car.make}</option>
			</c:forEach>
		</select> 
		
		&nbsp;<label>Year</label>
		<input type="hidden" name="yearInput" id="yearInput"/>
		<select id="year" onChange="setInputValue('year')">
			<option value="invalid">---</option>
			<c:forEach var="car" items="${cars}" varStatus="loop">
				<option value="${car.year}">${car.year}</option>
			</c:forEach>
		</select>
		Year Offset 
		<input size="3" type="number" name="yearOffset" width="10" max="5"/>
		
		&nbsp;<label>Color</label>
		<input type="hidden" name="colorInput" id="colorInput"/>
		<select id="color" onChange="setInputValue('color')">
			<option value="invalid">---</option>
			<c:forEach var="car" items="${cars}" varStatus="loop">
				<option value="${car.color}">${car.color}</option>
			</c:forEach>
		</select>

		&nbsp;<label>Availability</label>
		<input type="hidden" name="availabilityInput" id="availabilityInput"/>
		<select id="availability" onChange="setInputValue('availability')">
			<option value="invalid">---</option>
			<option value="Available">Available</option>
			<option value="Reserved">Reserved</option>
			<option value="Rented">Rented</option>
		</select>

		&nbsp; 
		<input type="submit" value="Search" class="btn btn-primary btn-md"/>
		
		<a href="/clerkLogout" class="btn btn-primary btn-md">Logout</a>
		<a href="/clerkBackToHome" class="btn btn-primary btn-md">Back To Home</a>

<!-- 		&nbsp;  -->
<!-- 		<input class="custom-control-input" id="showOnlyAvailable" type="checkbox"/> -->
<!--         <label class="custom-control-label" for="checkbox-large"> -->
<!--             Show Only Available -->
<!--         </label> -->
</div>
</div>
</form>
<br>


	<div class="container">
	<table id="myTable" class="sortable">
		<tr class="header">
			<th style="width: 20%;">Model</th>
			<th style="width: 20%;">Type</th>
			<th style="width: 20%;">Make</th>
			<th style="width: 10%;">Year</th>
			<th style="width: 10%;">Color</th>
			<th style="width: 10%;">Availability For Rent</th>
		</tr>
		
		<c:forEach var="car" items="${cars}" varStatus="loopCounter">
		<tr class="CarInfo">
			<td>${car.model}</td>
			<td>${car.type}</td>
			<td>${car.make}</td>
			<td>${car.year}</td>
			<td>${car.color}</td>
			<td>
			<form method = "post">  
				<input value="${car.licensePlateNumber}"  type = "hidden" name = "licensePlateInput" />
			
				<input class="btn btn-primary btn-sm" type="submit" formaction="/carDetailView" value="${car.availableReservedOrRented}" />
			 </form>
			</td>
		</tr>
		</c:forEach>
		
	</table>
	</div>
	
</body>