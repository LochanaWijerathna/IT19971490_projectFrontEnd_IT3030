package recources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import util.DBConnection;

public class Consumption {
	
	//DBConnection object to connect to database
	DBConnection dbConnect = new DBConnection();
	String dbErrorMessage = "Error while connecting to the database for reading.";
	
	// ---Method to insert the consumption details---
	public String insertConsumption(String userID, String month, String premonreading, String curmonreading){
		String output = "";
	
		try{

			Connection con = dbConnect.connect();
			if (con == null){
				return dbErrorMessage; 	
			}
			// create a prepared statement
			String query = " insert into consumption(conID,userID,month,preMonReading,curMonReading,conUnits) VALUES (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, userID);
			preparedStmt.setString(3, month);
			preparedStmt.setInt(4, Integer.parseInt(premonreading));
			preparedStmt.setInt(5, Integer.parseInt(curmonreading));
			
			// calculate Monthly consumption units
			int conunits;
			conunits = (Integer.valueOf(curmonreading) - Integer.valueOf(premonreading));
			preparedStmt.setInt(6, conunits);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newConcepts = readConsumption();
			output = "{\"status\":\"success\", \"data\": \"" +newConcepts + "\"}";
			
		}catch (Exception e){
			output = "{\"status\":\"error\", \"data\":\"Error while launching the consumption\"}";
			System.err.println(e.getMessage());
		}
			
		return output;
		
	}
	
	// ---Method to update a consumption details---
	public String updateConsumption(String conId, String userID, String month, String premonreading, String curmonreading) {
		
		String output = "";
		try{
			
			Connection con = dbConnect.connect();
			if (con == null){
				return dbErrorMessage; 	
			}
		
			// create a prepared statement
			String query = "UPDATE consumption SET userID=?, month=?, preMonReading=?, curMonReading=?, conUnits=? WHERE conID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
		
			// binding values
			preparedStmt.setString(1, userID);
			preparedStmt.setString(2, month);
			preparedStmt.setInt(3, Integer.parseInt(premonreading));
			preparedStmt.setInt(4, Integer.parseInt(curmonreading));
			
			// calculate updated monthly consumption units
			int conunits;
			conunits = (Integer.valueOf(curmonreading) - Integer.valueOf(premonreading));
			preparedStmt.setInt(5, conunits);
			preparedStmt.setInt(6, Integer.parseInt(conId));
		
			// execute the statement
			preparedStmt.execute();
		
			con.close();
			
			String newItems = readConsumption();
			output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
		
		}catch (Exception e){
			output = "{\"status\":\"error\", \"data\":\"Error while updating the consumption\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	// ---Method to delete a consumption detail---
	public String deleteConsumption(String conID){
		String output = "";
		try{
			
			Connection con = dbConnect.connect();
			if (con == null){
				return dbErrorMessage; 	
			}
			// create a prepared statement
			String query = "DELETE FROM consumption WHERE conID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(conID));
			
			// execute the statement
			preparedStmt.execute();

			con.close();
			
			String newItems = readConsumption();
			output = "{\"status\":\"success\", \"data\": \"" +newItems + "\"}";
			
		}catch (Exception e){
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the consumption\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	// ---Method to read all Consumption---
	public String readConsumption(){
		String output = "";
		try{
			Connection con = dbConnect.connect();
			if (con == null){
				return dbErrorMessage; 	
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Consumption ID</th><th>User ID</th>" +
			"<th>Month</th>" +
			"<th>Previous Month Reading</th>" +
			"<th>Current Month Reading</th>" +
			"<th>Consumption units</th>" +
			"<th>Update</th><th>Delete</th></tr>";
			String query = "select * from consumption";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()){
				String conID = Integer.toString(rs.getInt("conID"));
				String userID = rs.getString("userID");
				String month = rs.getString("month");
				String premonread = Integer.toString(rs.getInt("preMonReading"));
				String curmonread = Integer.toString(rs.getInt("curMonReading"));
				String conunits = Integer.toString(rs.getInt("conUnits"));
			
				// Add into the html table
				output += "<tr><td>" + conID + "</td>";
				output += "<td>" + userID + "</td>";
				output += "<td>" + month + "</td>";
				output += "<td>" + premonread + "</td>";
				output += "<td>" + curmonread + "</td>";
				output += "<td>" + conunits + "</td>";
			
				// button for updating a consumption
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-conID='" + conID + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-conID='" + conID + "'></td></tr>";
				

			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
			
		}catch (Exception e){
			output = "Error while reading the consumptions.";
			System.err.println(e.getMessage());	
		}
			
		return output;
			
	}
	

}
