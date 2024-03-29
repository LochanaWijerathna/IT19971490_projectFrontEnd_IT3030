<%-- <%@page import="util.DBConnection"%>
<%@page import="recources.overallMonitoring"%> --%>
<%@ page import="com.OverallMonitoring"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Overall Monitoring Management</title>
	
	<!-- Linking the css scripts -->
	<link rel="stylesheet" href="Views/bootstrap.min.css">
	<link rel="stylesheet" href="Views/form.css">
	
	<!-- Linking the js files -->
	<script src="Components/jquery-3.6.0.min.js"></script>
	<script src="Components/Monitoring.js"></script>
	
</head>
<body>

		
	<div class="container">
	<div class="row">
	<div class="col-12">
		<h1 align="center">Overall Monitoring Management</h1>
		
		<!--------------------- Start of form  ------------------------------->
		<form id="formItem" name="formItem">
			
            <input id="pay_ID" name="pay_ID" type="text" class="form-control form-control-sm" placeholder="Payment ID">
			<br> 
		
			<input id="power_consumption_ID" name="power_consumption_ID" type="text" class="form-control form-control-sm" placeholder="Power Consumption ID">
			<br> 
            
            <input id="month" name="month" type="text" class="form-control form-control-sm" placeholder="Month">
			<br> 
			
			<input id="comment" name="comment" type="text" class="form-control form-control-sm" placeholder="Comment">
			<br> 
            
			<input id="btnSave" name="btnSave" type="button" value="Add Consumption" class="btn btn-primary">
            <input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
		</form>
		<!--------------------- End of form  ------------------------------->
		
		<br>
		<!--------------------- Alerts  ------------------------------->
		<div id="alertSuccess" class="alert alert-success"></div>
		<div id="alertError" class="alert alert-danger"></div>
		<br>
		
		<!--------------------- Display concepts  ------------------------------->
		<div id="divItemsGrid">
		<%
		OverallMonitoring overallMonObj = new OverallMonitoring();
			out.print(overallMonObj.readOverallMonitoring());
		%>
		</div>

	</div>
	</div> 
	</div>
</body>
</html>