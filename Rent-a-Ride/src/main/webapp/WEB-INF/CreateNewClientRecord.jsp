<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<link href="css/bootstrap.min.css" rel="stylesheet" />
	<link href="css/custom.css" rel="stylesheet" />
	<script type="text/javascript" src="scripts/car-catalog-script.js"></script>  
	<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
	<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
</head>

<body>

<form method="post" class="form-horizontal">
<div class="container" align="center">
 <div class="form">
 <h2>Create New Client</h2>
 <br><br>
<div class="container col-md-12">	
<div class="form-group">
 
 	<div class="col-md-5"><label class="control-label pull-right pull-right">Driver Licence Number:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" type="text" name="driverLicenceNumber"></div>
 </div>
 <div class="form-group">
 
 	<div class="col-md-5"><label class="control-label pull-right">Client First Name:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" type="text" name="clientFirstName"></div>
 	</div>
 <div class="form-group">
 
 	<div class="col-md-5"><label class="control-label pull-right">Client Last Name:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" type="text" name="clientLastName"></div>
 	</div>
 <div class="form-group">
 
 	<div class="col-md-5"><label class="control-label pull-right">Client Phone Number:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" type="number" name="phoneNumber"></div>
 </div>
 <div class="form-group">
 	<div class="col-md-5"><label class="control-label pull-right">Licence Expiry Date:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" type="date" name="licenceExpiryDate" placeholder="yyyy-mm-dd"></div>
</div>
<br>
</div>  
</div>
</div>
 <div class="form-group" align="center">
  	<button type="submit" class="btn btn-primary btn-md" formaction="/goToClientManagementPageAfterCreation">Confirm</button>
  	<a href="/ClientManagementPage" class="btn btn-primary btn-md">Back</a>
 </div>
 
</form>
</body>
</html>