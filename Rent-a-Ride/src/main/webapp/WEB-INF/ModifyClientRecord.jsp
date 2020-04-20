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
 <h2>Modify Client Record</h2>
 <br><br>
<div class="form-group">
 
 	<div class="col-md-5"><label class="control-label pull-right pull-right">Driver Licence Number:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" value = "${client.driverLicenceNumber}" type="text" name="driverLicenseNumber" readonly></div>
 </div>
 <div class="form-group">
 
 	<div class="col-md-5"><label class="control-label pull-right">Client First Name:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" value = "${client.clientFirstName}" type="text" name="clientFirstName" ${readOnly}></div>
 	</div>
 <div class="form-group">
 
 	<div class="col-md-5"><label class="control-label pull-right">Client Last Name:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" value = "${client.clientLastName}" type="text" name="clientLastName" ${readOnly}></div>
 	</div>
 <div class="form-group">
 
 	<div class="col-md-5"><label class="control-label pull-right">Client Phone Number:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" value = "${client.phoneNumber}"  type="number" name="phoneNumber" ${readOnly}></div>
 </div>
 <div class="form-group">
 	<div class="col-md-5"><label class="control-label pull-right">Licence Expiry Date:</label></div>
 	<div class="col-md-3"><input class="form-control pull-left" value = "${client.licenceExpiryDate}"  type="date" name="licenceExpiryDate" placeholder="yyyy-mm-dd" ${readOnly}></div>
</div>

</div>  
</div>
<br>
<div align="center">
 <div class="form-group">
  	<input value = "${client.driverLicenceNumber}" class="pull-left" type="hidden" name="driverLicenseNumberInput">
  	<button type="submit" class="btn btn-primary btn-md" formaction="/gotoClientManagementPageAfterModification" 
  	name ="ConfirmModify" ${disableOrNot} >Modify Now</button>
  	<button type="submit" formaction="/backToClientManagementPage" class="btn btn-primary btn-md">Back</a>
 </div>
 </div>
 
</form>
</body>
</html>