package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OverallMonitoring {
	

	
	//A common method to connect to the DB
//A common method to connect to the DB
private Connection connect(){
	Connection con = null;
	try{
		Class.forName("com.mysql.cj.jdbc.Driver");

		//Provide the correct details: DBServer/DBName, username, password
		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "root");
		
	}catch (Exception e){
		e.printStackTrace();
	}
	
	return con;
}
		
	
	
	//Insert Project Details
	public String insertOverallMonitoring(String monitoring_ID, String pay_ID, String power_consumption_ID, String month, String comment){
		String output = "";
		try{
			Connection con = connect();
				if (con == null){
					return "Error while connecting to the database for inserting."; 
			}
			
				
				//double query2 = Double.valueOf("SELECT SUM(balance) FROM payment WHERE month='"+month+"'");
				String query2 = "SELECT SUM(balance) as totalBalance FROM payment WHERE month='"+month+"'";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query2);
				rs.next();
				String balance = rs.getString(1);
				//System.out.println("hi1");
				//System.out.println(balance);
				
				//String balance = rs.getString("totalBalance");
				
				double totbalance = Double.valueOf(balance);
				//System.out.println(totbalance);
				
				//int query3 = Integer. valueOf("SELECT SUM(units) FROM consumption WHERE month='"+month+"'");
				String query3 = "SELECT SUM(conUnits) as totalUnits FROM consumption WHERE month='"+month+"'";
				Statement stmt1 = con.createStatement();
				ResultSet rs1 = stmt1.executeQuery(query3);
				rs1.next();
				String units = rs1.getString(1);
				//System.out.println(units);
				//System.out.println("hi2");
				
				int totunits = Integer.valueOf(units);
				//System.out.println(totunits);
				
				
				
				// create a prepared statement
				//String query = " insert into monitoring('monitoring_ID', 'pay_ID', 'power_consumption_ID', 'month', 'units', 'comment') VALUES (?, ?, ?, ?, ?, ?, ?)";
				String query = "insert into monitoring(monitoring_ID, pay_ID, power_consumption_ID, month, units, balance, comment) VALUES (?, ?, ?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setInt(2, Integer.parseInt(pay_ID));
				//preparedStmt.setInt(2, 1);
				preparedStmt.setInt(3, Integer.parseInt(power_consumption_ID));
				//preparedStmt.setInt(3, 6);
				preparedStmt.setString(4, month);
				//preparedStmt.setString(4, "Aug-2022");
				
				preparedStmt.setInt(5, totunits);
				//preparedStmt.setInt(5, 234);
				
				preparedStmt.setDouble(6, totbalance);
				//preparedStmt.setDouble(6, 234);
				preparedStmt.setString(7, comment);
				//preparedStmt.setString(7, "comment");
				
				preparedStmt.execute();
				con.close();
				 
				 String newMonitoring = readOverallMonitoring(); 
				 output = "{\"status\":\"success\", \"data\": \"" + newMonitoring + "\"}";
				 
				 }catch (Exception e)
				 {
					 
					 output = "{\"status\":\"error\", \"data\":\"Error while inserting the Inquiry to system.\"}"; 
					 System.err.println(e.getMessage());
				 }
		 return output;
	 }
	
	
	// Method for create Table and Get Data from the Database
	
	//read
	public String readOverallMonitoring(){
		String output = "";
		
		try{
			Connection con = connect();
			if (con == null){
				return "Error while connecting to the database for reading."; 	
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Monitoring ID</th><th>Payment ID</th>" +
			"<th>Power Consumption ID</th>" +
			"<th>month</th>" +
			"<th>units</th><th>balance</th><th>comment</th></tr>"+
			"<th>Update</th><th>Remove</th></tr>";
			String query = "select * from monitoring";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()){
				String monitoring_ID = Integer.toString(rs.getInt("monitoring_ID"));
				String pay_ID = Integer.toString(rs.getInt("pay_ID"));
				String power_consumption_ID = Integer.toString(rs.getInt("power_consumption_ID"));
				String month = rs.getString("month");
				String units = Integer.toString(rs.getInt("units"));
				String balance = Double.toString(rs.getDouble("balance"));
				String comment = rs.getString("comment");
			
				// Add into the html table
				output += "<tr><td>" + monitoring_ID + "</td>";
				output += "<td>" + pay_ID + "</td>";
				output += "<td>" + power_consumption_ID + "</td>";
				output += "<td>" + month + "</td>";
				output += "<td>" + units + "</td>";
				output += "<td>" + balance + "</td>";
				output += "<td>" + comment + "</td>";
			
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-userid='" +monitoring_ID + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-userid='" + monitoring_ID + "'></td></tr>";
			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
			
		}catch (Exception e){
			output = "Error while reading the Overall Monitoring.";
			System.err.println(e.getMessage());	
		}
			
		return output;
		
	}
	
	
	//update
		public String updateMonitoring(String monitoring_ID, String pay_ID, String power_consumption_ID, String month,String comment) {
			
			String output = "";
			try{
				
				Connection con = connect();
				if (con == null){
					return "Error while connecting to the database for updating."; 	
				}
			
				// create a prepared statement
				String query = "UPDATE monitoring SET pay_ID=?, power_consumption_ID=?, month=?, comment=?  WHERE monitoring_ID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
			
				// binding values

				preparedStmt.setInt(1, Integer.parseInt(pay_ID));
				preparedStmt.setInt(2, Integer.parseInt(power_consumption_ID));
				preparedStmt.setString(3, month); 
				preparedStmt.setString(4, comment); 
				preparedStmt.setInt(5, Integer.parseInt(monitoring_ID)
				);
			
				// execute the statement
				preparedStmt.execute();
			
				con.close();
				String newItems = readOverallMonitoring();
				output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
			
			}catch (Exception e){
				output = "{\"status\":\"error\", \"data\":\"Error while updating the comment\"}";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
	  
		//Delete
		public String deleteMonitoring(String monitoring_ID){
			String output = "";
			try{
				
				Connection con = connect();
				if (con == null){
					return "Error while connecting to the database for deleting."; 	
				}
				// create a prepared statement
				String query = "DELETE FROM monitoring  WHERE monitoring_ID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setString(1, monitoring_ID);
				
				// execute the statement
				preparedStmt.execute();

				con.close();
				String newItems = readOverallMonitoring();
				output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
				
			}catch (Exception e){
				output = "{\"status\":\"error\", \"data\":\"Error while deleting the Overall Monitoring\"}";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
	 
	

}