<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />


	<link href="css/bootstrap.min.css" rel="stylesheet" />
	<link href="css/custom.css" rel="stylesheet" />
	<script type="text/javascript" src="scripts/car-catalog-script.js"></script>  
	<link href="styles/car-catalog-style.css" rel="stylesheet" type="text/css">
	<script src="https://www.kryogenix.org/code/browser/sorttable/sorttable.js"></script>
</head>

<body>
<br><br>

		<div class="container col-md-12" align="center">
				<div class="row">
					<h1>Clerk Home Page</h1>
				</div>
				<br><br>				
			
				<!-- ../../webapp/WEB-INF/jsp/login.jsp -->
				<br><Br>
				<a href="/showClientManagement" class="btn btn-primary btn-lg">Manage Clients</a> 
				
				<a href="/viewCarCatalog" class="btn btn-primary btn-lg">View Catalog</a>
				
				<a href="/clerkLogout" class="btn btn-primary btn-lg">Logout</a> 
		</div>
</body>

</html>