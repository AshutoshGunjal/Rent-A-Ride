<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<!-- 	<meta http-equiv="refresh" content="46 ;url=/backfromRental"> -->
	<link href="css/bootstrap.min.css" rel="stylesheet" />
	<link href="css/custom.css" rel="stylesheet" />
	<script type="text/javascript" src="scripts/car-catalog-script.js"></script>  
	<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
	<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
</head>

<body>

<form method="post">


<div class="container" align="center">
 <div class="form">
 <h2>Create A New Rental</h2>
 <br><br>
<div align="center">


<div class="form">
 	<div class="col-md-6"><label class="pull-right">Car License Number:</label></div>
 	<div class="col-md-6"><input value = "${licensePlateNumber}" class="pull-left" type="text" name="CarLicenseNo" readonly></div>
 
 </div>
 <br>
  <div class="form">

 	<div class="col-md-6"><label class="pull-right">Client Drivers License:</label></div>
 	<div class="col-md-6"><input value ="${driverLicenseNumber}" class="pull-left" type="text" name="driverLicenceNumber"></div>
 	<div><button type="submit" class="btn btn-primary btn-sm" formaction="/searchThisClient">Search this Client</button></div>
 	<div><font color="red">${clientNotFoundMessage}</font></div>
 	</div>
 <br>
 <div class="form">
 
 	<div class="col-md-6"><label class="pull-right">Client First Name:</label></div>
 	<div class="col-md-6"><input value ="${clientFirstName}" class="pull-left" type="text" name="clientFirstName"></div>
 	</div>
 <br>
 <div class="form">
 
 	<div class="col-md-6"><label class="pull-right">Client Last Name:</label></div>
 	<div class="col-md-6"><input value ="${clientLastName}" class="pull-left" type="text" name="clientLastName"></div>
 	</div>
 <br>
 <div class="form">
 
 	<div class="col-md-6"><label class="pull-right">Client Mobile:</label></div>
 	<div class="col-md-6"><input value ="${phoneNumber}"  class="pull-left" type="text" name="phoneNumber"></div>
 	</div>
 <br>

 <div class="form">
 
 	<div class="col-md-6"><label class="pull-right">Licence Expiry Date:</label></div>
 	<div class="col-md-6"><input value ="${licenceExpiryDate}" class="pull-left" type="date" name="licenceExpiryDate" placeholder="yyyy-mm-mdd"></div>
</div>
 <br>
 <div class="form" onload="getCurrentDate()">
 
 	<div class="col-md-6"><label class="pull-right">Vehicle Pickup Date:</label></div>
 	<div class="col-md-6"><input value ="${pickUpDate}"  class="pull-left" type="date" id ="now" name="pickupDate" placeholder="yyyy-mm-dd" readonly></div>
 	</div>
 <div class="form">
 	
 	<div class="col-md-6"><label class="pull-right">Vehicle Drop-off Date:</label></div>
 	<div class="col-md-6"><input value ="${dropOffDate}" class="pull-left" type="date" name="dropoffDate"></div>
 	</div>
</div>  
   </div>
 <div>
 <br>
 <div align="center">
  	<button type="submit" class="btn btn-primary btn-sm" formaction="/carRented">Confirm Rental</button>
  	
  	<button type="submit" class="btn btn-primary btn-sm" id = "toClick" formaction="/backfromRental">Back</button>
 </div>
 </div>
 
 
 <br>
<p align = "center" id = "goBacktoCarDetails"></p>

<script type="text/javascript">


		var counter = 45;
		var timerElement = document.getElementById('goBacktoCarDetails');
	
		var timerId = setInterval(countdown, 1000);
		
		function countdown() {
		
			if(counter == 0){
				timerElement.innerHTML = "Reloading Availability Data Now!!";
				document.getElementById("toClick").click();
				}
			else if  (counter < 5) {
				timerElement.innerHTML = counter + ' seconds remaining' + "...Reloading Data Shortly!!";
				counter--;
			} 
			
				else {
				timerElement.innerHTML = counter + ' seconds remaining for data refresh';
				counter--;
			}
		}
</script>

<br>
 
 
 </div>
 
</form>
</body>
</html>






<!--  -->