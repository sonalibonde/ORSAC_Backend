<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
      <form action="rest/CallService/userdetails" method="post">
		<p>
			UserName : <input type="text" name="user" />
			Password  : <input type="text" name="password" />
		</p>
		<input type="submit" value="GetDetails" />
		
			Vehicle No  : <input type="text" name="vehicle" />
		
			
		
	</form>
	<form action="rest/CallService/vehicledetails" method="post">
		
			Vehicle No  : <input type="text" name="vehicle" />
		<input type="submit" value=" getVehicle" />
			
		
	</form>
</body>
</html>