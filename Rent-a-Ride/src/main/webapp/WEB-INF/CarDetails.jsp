<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Refresh" content="6	;url=/refreshCarDetails">

<link href="css/bootstrap.min.css" rel="stylesheet" />
<link href="css/custom.css" rel="stylesheet" />
<script type="text/javascript" src="scripts/car-catalog-script.js"></script>  
<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>

</head>
<body background="grey">


<div class="container" align="center">
<h2 style="text-align:center"><strong>${car.make} ${car.model} Details</strong></h2>



<img src="images/car1.jpg" style="width:350px;height:200px;">
 <p class="price">$ ${car.price}</p>
 
 <div class="form">
 <div class="col-md-6"><label class="pull-right">Model:</label></div>
 <div class="col-md-6"><label class="pull-left">${car.model}</label></div>
 </div>
 
  <div class="form">
 <div class="col-md-6"><label class="pull-right">Make:</label></div>
 <div class="col-md-6"><label class="pull-left">${car.make}</label></div>
 </div>
 
  <div class="form">
 <div class="col-md-6"><label class="pull-right">Year:</label></div>
 <div class="col-md-6"><label class="pull-left">${car.year}</label></div>
 </div>
 
  <div class="form">
 <div class="col-md-6"><label class="pull-right">Car:</label></div>
 <div class="col-md-6"><label class="pull-left">${car.type}</label></div>
 </div>
 
 
 <div class="form">
 <div class="col-md-6"><label class="pull-right">Color:</label></div>
 <div class="col-md-6"><label class="pull-left">${car.color}</label></div>
 </div>
</div>
 
 <br>
 
 <div align="center">
  <form method="post">
	  <input value="${car.licensePlateNumber}" type = "hidden" name = "licensePlate" />
	  
	  <input type="submit" id = "resButtoon"   class="btn btn-primary btn-sm" 
	  					   formaction="/reserveThisCar" value="${canReserveOrNot}"  ${disableOrNo}/>
	  					   
	  <input type="submit" id = "rentButtoon"  class="btn btn-primary btn-sm" 
	  					   formaction="/rentThisCar" value="${canRentOrNot}"  ${disableOrNo} />
	  
	  <input type="hidden" name="carId" id="carId" value="${car.id}"/>
	  					   
		<a href="/back" class="btn btn-primary btn-sm">Back</a>
		<a href="/next" class="btn btn-primary btn-sm">Next</a>
		
		<a href="/backtoCarCatalog" class="btn btn-primary btn-sm">Back to Car Catalog</a>
</form>
</div>
<br>
<p align = "center" id = "refreshPage"></p>

<script type="text/javascript">


		var counter = 5;
		var timerElement = document.getElementById('refreshPage');
		
		var timerId = setInterval(countdown, 1000);
		
		function countdown() {
		
			if(counter == 0){
				timerElement.innerHTML = "Reloading Availability Data Now!!";
				}
			else if  (counter < 2) {
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

</body>
</html>