package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/OverallMonitoringAPI")
public class OverallMonitoringAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OverallMonitoring MonitoringObj = new OverallMonitoring();
       

    public OverallMonitoringAPI() {
        super();
        
    }

    //API Method for Get Data
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	//API Method for Insert Data
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String output = MonitoringObj.insertOverallMonitoring(
				request.getParameter("hidItemIDSave"),
				request.getParameter("pay_ID"),
				request.getParameter("power_consumption_ID"),
				request.getParameter("month"),
				request.getParameter("comment")
				);
response.getWriter().write(output);
	}

	
	  //API Method for Update the Data
	  
	  protected void doPut(HttpServletRequest request, HttpServletResponse
	  response) throws ServletException, IOException {
	  
	  
	  Map paras = getParasMap(request); 
	  String output =
	  MonitoringObj.updateMonitoring(paras.get("hidItemIDSave").toString(),
			  paras.get("pay_ID").toString(),
			  paras.get("power_consumption_ID").toString(),
			  paras.get("month").toString(),
	  paras.get("comment").toString());
	  response.getWriter().write(output); }
	  
	  
	  //API Method for Delete Data
	  
	  protected void doDelete(HttpServletRequest request, HttpServletResponse
	  response) throws ServletException, IOException {
	  
	  
	  Map paras = getParasMap(request);
	  
	  String output = MonitoringObj.deleteMonitoring(paras.get("monitoring_ID").toString());
	  response.getWriter().write(output); } // Convert request parameters to a Map
	  private static Map getParasMap(HttpServletRequest request) {
	  
	  Map<String, String> map = new HashMap<String, String>(); try{
	  
	  Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); String
	  queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
	  scanner.close(); String[] params = queryString.split("&"); for (String param
	  : params) {
	  
	  String[] p = param.split("="); map.put(p[0], p[1]); } }catch (Exception e) {
	  
	  } return map; }
	 

}
