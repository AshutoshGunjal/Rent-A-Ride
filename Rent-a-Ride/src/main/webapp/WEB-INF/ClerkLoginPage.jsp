<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
	
<!DOCTYPE html>

<html>
	<head>
		<link href="styles/universal.css" rel="stylesheet" type="text/css"/>
		<meta http-equi="Content-Type" content="text/html; charset=UTF-8">
		<title>Login</title>
	</head>
	
	<link href="css/bootstrap.min.css" rel="stylesheet" />
	<link href="css/custom.css" rel="stylesheet" />
	
	
	<body>
	<div class="container" align="center">
	<div>
		<font color="red">${errorMessage}</font>
	</div>	
	
	<form method="post" class="form-horizontal">
	<div class="container col-md-12">	
		<div>
			<h2>Clerk: Sign In</h2>
			<br><br>
				<div class="form-group">
					<div class="col-md-5">
						<label class="control-label pull-right">Username</label>
					</div>
					<div class="col-md-3">
						<input class="pull-left form-control" type="email" name="email" placeholder="Email Id">
					</div>
				</div>
				<div class="form-group">
				<div class="col-md-5">
					<label class="control-label pull-right">Password</label>
				</div>
				<div class="col-md-3">	
					<input class="pull-left form-control" type="password" name="password" placeholder="Password">	
				</div>
				</div>
				<br><br>
				<div class="form-group">
						<button type="submit" class="btn btn-primary btn-md" formaction="/tryTologinAsClerk" value="Sign In" >Login</button>
						<button type="submit"  class="btn btn-primary btn-md" formaction="/tryToRegisterAsClerk" value="Sign Up" >Register</button>
						<button type="reset"  class="btn btn-primary btn-md" >Reset</button>
				</div>
				
		</div>
		</div>
	</form>
	</div>
</body>
</html>