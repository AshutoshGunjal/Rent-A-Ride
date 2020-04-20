<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
	
<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/custom.css" rel="stylesheet" />
<script type="text/javascript" src="scripts/car-catalog-script.js"></script>  
<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>

</head>
<body>

<form>
<div class="container">
<h3>Car Catalog Management</h3>

	<div class="col-md-6">
		<h3>
			<a href="/adminLogout"><u>Logout</u></a>
			&nbsp; &nbsp;
			<a href="/adminHomePage"><u>Back To Home Page</u></a>
		</h3>
		<font color="red">${errorMessage}</font>
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
		&nbsp;<label>Year Offset</label>			
		<input size="3" type="number" maxlength="3" name="yearOffset" width="10" max="5"/>
		
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
		<button type="submit" formaction="/filterCarsForAdmin" formmethod="post">
			<i class="fa fa-search text-green" aria-hidden="true"></i>
		</button>
		<input class="pull-right btn btn-primary btn-sm" type="submit" formmethod="post" formaction="/addNewCarPage" value="Add">
		
<!-- 		<input type="submit" value="Search" class="btn btn-primary btn-md pull-right"/> -->
</div>
</div>
</form>
<br>

<form>
	<div class="container">
	<table id="myTable" class="sortable">
		<tr class="header">
			<th style="width: 20%;">License Plate Number</th>
			<th style="width: 10%;">Model</th>
			<th style="width: 10%;">Type</th>
			<th style="width: 10%;">Make</th>
			<th style="width: 5%;">Year</th>
			<th style="width: 10%;">Color</th>
			<th style="width: 5%;">Price</th>
			<th style="width: 10%;">Availability</th>
			<th style="width: 13%;">Operations</th>
		</tr>
		
		<c:forEach var="car" items="${cars}" varStatus="loop">
		<tr class="CarInfo">
			<td>${car.licensePlateNumber}</td>
			<td>${car.model}</td>
			<td>${car.type}</td>
			<td>${car.make}</td>
			<td>${car.year}</td>
			<td>${car.color}</td>
			<td>$${car.price}</td>
			<td>${car.availableReservedOrRented}</td>
			<td>  
				<form method = "post">  
					<span> 
							<button type="submit" formaction="/editCar">
								<i class="fa fa-edit"></i>
							</button>
					</span>
					<span>
						<button type="submit" formaction="/deleteCar">
							<i class="fa fa-trash"></i>
						</button>
					</span>
					<span>
						
					</span>
					<input type="hidden" name="currentLicensePlateNumber" value="${car.licensePlateNumber}">
				</form>
			</td>
			
		</tr>
		</c:forEach>
		
	</table>
	</div>
	</form>
</body>