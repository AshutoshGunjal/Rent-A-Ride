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
<div class="container" align="center">
<div>
	<font color="red">${errorMessage}</font>
</div>		
	<form name="myForm" method="post" class="form-horizontal">
	<div class="container col-md-12">	
	<h2>Clerk Registration</h2>
	<br><br>
	<div class="form-group">
		<div class="col-md-5">
			<label class="control-label pull-right">Username</label>
		</div>
		<div class="col-md-3">
			<input class="form-control pull-left" type="email" name="email" placeholder="Email Id">
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-md-5">
			<label class="control-label pull-right">Password</label>
		</div>
		<div class="col-md-3">
			<input class="form-control pull-left" type="password" name="password" placeholder="Password">	
		</div>
	</div>
	
	<br>
	<br>
	<div class="form-group">
		<button type="submit"  formaction="/clerkRegistered" class="btn btn-primary btn-md">Register</button>
		<button type="reset"  class="btn btn-primary btn-md" >Reset</button>
	</div>
	</div>
	</form>
</div>	
</body>
</html>