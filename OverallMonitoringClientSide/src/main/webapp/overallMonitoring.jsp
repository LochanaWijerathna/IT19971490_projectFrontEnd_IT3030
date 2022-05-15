<%@page import="util.DBConnection"%>
<%@page import="recources.overallMonitoring"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Consumption Monitoring Management</title>
	
	<!-- Linking the css scripts -->
	<link rel="stylesheet" href="Views/bootstrap.min.css">
	<link rel="stylesheet" href="Views/form.css">
	
	<!-- Linking the js files -->
	<script src="Components/jquery-3.2.1.min.js"></script>
	<script src="Components/concept.js"></script>
	
</head>
<body>

	<% DBConnection userConn = new DBConnection(); %>
		
	<div class="container">
	<div class="row">
	<div class="col-12">
		<h1 align="center">Consumption Monitoring Management</h1>
		
		<!--------------------- Start of form  ------------------------------->
		<form id="formCon" name="formCon">
			
            <input id="pay_ID" name="pay_ID" type="text" class="form-control form-control-sm" placeholder="pay_ID">
			<br> 
		
			<input id="power_consumption_ID" name="power_consumption_ID" type="text" class="form-control form-control-sm" placeholder="power_consumption_ID">
			<br> 
            
            <input id="month" name="month" type="text" class="form-control form-control-sm" placeholder="month">
			<br> 
			
			<input id="comment" name="comment" type="text" class="form-control form-control-sm" placeholder="comment">
			<br> 
            
			<input id="btnSave" name="btnSave" type="button" value="Add Consumption" class="btn btn-primary">
            <input type="hidden" id="hidConIDSave" name="hidConIDSave" value="">
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
		overallMonitoring overallMonObj = new overallMonitoring();
			out.print(overallMonObj.readOverallMonitoring());
		%>
		</div>

	</div>
	</div> 
	</div>
</body>
</html>