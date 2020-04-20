<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet" />
	<link href="css/custom.css" rel="stylesheet" />
	<script type="text/javascript" src="scripts/car-catalog-script.js"></script>  
	<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
	<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>

<meta charset="ISO-8859-1">
<title>Client List</title>
</head>
<body>	

<form method = post>

<div class="col-md-6">
	<h2>Client List</h2> 
	</div>
<div class="col-md-6"align="right" >
		<h3>
			<a href="/clerkLogout" class="btn btn-primary btn-lg"><u>Logout</u></a>
			<a href="/clerkBackToHome" class="btn btn-primary btn-lg"><u>Back To Home</u></a>
			<a href="/createNewClient" class="btn btn-primary btn-lg"><u>Create New Client</u></a>
		</h3>
		<font color="red">${errorMessage}</font>
</div> 

<hr style="clear:both;"/>
<div class="container">
	<table id="myTable" class="sortable">
		<tr class="header">
			<th style="width: 25%;">Driver License Number</th>
			<th style="width: 10%;">First Name</th>
			<th style="width: 10%;">Last Name</th>
			<th style="width: 10%;">Phone Number</th>
			<th style="width: 25%;">Expiry Date</th>
			<th style="width: 10%;">Modification</th>
			<th style="width: 10%;">Deletion</th>
		</tr>
		
		<c:forEach var="client" items="${clients}" varStatus="loopCounter">
		<tr class="CarInfo">
			<td>${client.driverLicenceNumber}</td>
			<td>${client.clientFirstName}</td>
			<td>${client.clientLastName}</td>
			<td>${client.phoneNumber}</td>
			<td>${client.licenceExpiryDate}</td>
			
			<input value= "${client.editing}" type = "hidden" name = "editing" />
		
			
			
			<form method="post" >
			<td>  
			<input value= "${client.driverLicenceNumber}" type = "hidden" name = "driverLicenceNumberForModify" />
			<input class="btn btn-primary btn-sm" type= "submit" formaction= "/gotoModifyClientRecord" value="Modify" />
			</td>
			
			<td>  
			<input value= "${client.driverLicenceNumber}" type = "hidden" name = "driverLicenceNumberForDelete" />
			<input class="btn btn-danger btn-sm" type= "submit" formaction= "/gotoDeleteClientRecord" value= "Delete" />
			</td>
			</form>
		</tr>
		</c:forEach>
		
	</table>
	</div>
	</form>
</body>
</html>