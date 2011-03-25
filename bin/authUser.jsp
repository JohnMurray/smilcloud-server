<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,java.sql.*,mediaCloud.*,com.thoughtworks.xstream.XStream" %>  
<%
Connection connection = (Connection)application.getAttribute("dbconnection"); 
if (connection == null)
	System.out.println("***DB connect problem***");
%>
<% 
	String userName = request.getParameter("userName");
	String password = request.getParameter("password");
	XStream xstream = new XStream();
	User userTry = new User();
	userTry.setUserName(userName);
	userTry.setPassword(password);
	HashMap<String,String> hm = new HashMap<String,String>();
	if (userTry.isValid(connection)) {
		session.setAttribute("user_name", userTry.getUserId(connection));
		hm.put("success","true");
		hm.put("session","IDHERE");		
		String xml = xstream.toXML(hm);
		out.println(xml);
	}
	else {
		hm.put("success","false");
		String xml = xstream.toXML(hm);
		out.println(xml);
	}
%>