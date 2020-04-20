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
<div align="center">
 <div >
 <h2>Add new Car</h2>
 <br>
<div class="container" align="center">

<div class="form-group">
	 <div class="col-md-5">
	 	<label class="control-label pull-right">License Plate Number:</label>
	 </div>
	 <div class="col-md-3">
	 	<input class="form-control pull-left" type="text" name="licensePlateNumber">
	 </div>
 </div>
 
 <div class="form-group">
	 <div class="col-md-5">
	 	<label class="control-label pull-right">Description:</label>
	 </div>
	 <div class="col-md-3">
  		<textarea class="form-control rounded-0" name="carDescription" id="carDescription" rows="2"></textarea>
	 </div>
 </div>
 
 <div class="form-group">
 	<div class="col-md-5">
 		<label class="control-label pull-right">Model:</label>
 	</div>
 	<div class="col-md-3">
 		<input class="form-control pull-left" type="text" name="carModel">
 	</div>
 </div>

 <div class="form-group">
 	<div class="col-md-5">
 		<label class="control-label pull-right">Type:</label>
 	</div>
 	<div class="col-md-3">
 		<input class="form-control pull-left" type="text" name="carType">
 	</div>
 </div>

 <div class="form-group">
 	<div class="col-md-5">
 		<label class="control-label pull-right">Make:</label>
 	</div>
 	<div class="col-md-3">
 		<input class="form-control pull-left" type="text" name="carMake"></div>
 	</div>

 <div class="form-group">
 	<div class="col-md-5">
 		<label class="control-label pull-right">Year:</label>
 	</div>
 	<div class="col-md-3">
 		<input class="form-control pull-left" type="number" name="carYear">
 	</div>
 </div>

 <div class="form-group">
	<div class="col-md-5">
 		<label class="control-label pull-right">Color:</label>
	</div>
	<div class="col-md-3">
		<input class="form-control pull-left" type="text" name="carColor">
	</div>
</div>

 <div class="form-group">
	<div class="col-md-5">
 		<label class="control-label pull-right">Price:</label>
	</div>
	<div class="col-md-3">
		<input class="form-control pull-left" type="number" step="0.01" name="carPrice">
	</div>
</div>

<br>

 <div class="form-group">
	<div>
		<button type="submit" class="btn btn-primary btn-md" formaction="/addNewCar">Confirm</button>
	  	<a href="/adminManageCatalog" class="btn btn-primary btn-md">Back</a>
	</div>  
 </div>



</div>
</div>
</div>
</form>
</body>
</html>