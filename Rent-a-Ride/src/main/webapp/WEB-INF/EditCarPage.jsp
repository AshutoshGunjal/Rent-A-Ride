<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<meta http-equiv="Refresh" content="31	;url=/backToAdminManageCatalogAfterTimeout">
<head>
	<link href="css/bootstrap.min.css" rel="stylesheet" />
	<link href="css/custom.css" rel="stylesheet" />
	
	<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
	<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
	<script type="text/javascript" src="scripts/car-catalog-script.js"></script>  
	<!-- <script type="text/javascript" src="scripts/timer.js"></script> -->

    
</head>

<body>

<form method="post" class="form-horizontal">
<div align="center">
 <div>
 <h2> ${editDecide}</h2>
 <br>
 	
<br>
<div class="container" align="center">

<div class="form-group">
	 <div class="col-md-5">
	 	<label class="control-label pull-right">License Plate Number:</label>
	 </div>
	 <div class="col-md-3">
	 	<input value="${car.licensePlateNumber}" class="form-control pull-left" type="text" name="licensePlateNumber" readonly>
	 </div>
 </div>
 
 <div class="form-group">
	 <div class="col-md-5">
	 	<label class="control-label pull-right">Description:</label>
	 </div>
	 <div class="col-md-3">
  		<textarea class="form-control rounded-0" name="description" id="carDescription" rows="2" ${readOnly}>${car.description}</textarea>
	 </div>
 </div>
 
 <div class="form-group">
 	<div class="col-md-5">
 		<label class="control-label pull-right">Model:</label>
 	</div>
 	<div class="col-md-3">
 		<input value="${car.model}" class="form-control pull-left" type="text" name="model"  ${readOnly}>
 	</div>
 </div>

 <div class="form-group">
 	<div class="col-md-5">
 		<label class="control-label pull-right">Type:</label>
 	</div>
 	<div class="col-md-3">
 		<input value="${car.type}" class="form-control pull-left" type="text" name="type"  ${readOnly}>
 	</div>
 </div>

 <div class="form-group">
 	<div class="col-md-5">
 		<label class="control-label pull-right">Make:</label>
 	</div>
 	<div class="col-md-3">
 		<input value="${car.make}"  class="form-control pull-left" type="text" name="make"  ${readOnly}></div>
 	</div>

 <div class="form-group">
 	<div class="col-md-5">
 		<label class="control-label pull-right">Year:</label>
 	</div>
 	<div class="col-md-3">
 		<input value="${car.year}" class="form-control pull-left" type="number" name="year"  ${readOnly}>
 	</div>
 </div>

 <div class="form-group">
	<div class="col-md-5">
 		<label class="control-label pull-right">Color:</label>
	</div>
	<div class="col-md-3">
		<input value="${car.color}" class="form-control pull-left" type="text" name="color"  ${readOnly}>
	</div>
</div>

 <div class="form-group">
	<div class="col-md-5">
 		<label class="control-label pull-right">Price:</label>
	</div>
	<div class="col-md-3">
		<input value="${car.price}" class="form-control pull-left" step="0.01" name="price"  ${readOnly}>
	</div>
</div>

<div class="form-group">
	<div class="col-md-3">
		<input value="${car.availableReservedOrRented}" type="hidden" name="availableReservedOrRented"  ${readOnly}>
	</div>
</div>

<br>

 <div class="form-group">
	<div>
		<button type="submit" class="btn btn-primary btn-md" formaction="/saveCarChanges" ${disableOrNo}>Save Changes</button>
		<button type="submit" class="btn btn-primary btn-md"  id = "toClick" formaction="/backToAdminManageCatalog" >Cancel</button>
	  	
	  	
	</div>  
 </div>

<br>
<p id="timer"></p>
 	
<script type="text/javascript">


		var counter = 30;
		var timerElement = document.getElementById('timer');
		
		var timerId = setInterval(countdown, 1000);
		
		function countdown() {
		
			if(counter == 0){
				timerElement.innerHTML = "Time Up.. Rolling Back!!";
				document.getElementById("toClick").click();
				}
			else if  (counter < 5) {
				timerElement.innerHTML = counter + ' seconds remaining ' + "...Time ALmost Over!!";
				counter--;
			} 
			
				else {
				timerElement.innerHTML = counter + ' seconds remaining to complete modification';
				counter--;
			}
		}
</script>
<br>

</div>
</div>
</div>
</form>
</body>
</html>