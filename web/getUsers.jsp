<%
/**
* This will return all of the users that are registered in the system
*/
%>
<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,java.util.ArrayList,edu.nku.cs.csc440.team2.mediaCloud.*,com.thoughtworks.xstream.XStream" %>
<%
Connection connection = (Connection)application.getAttribute("dbconnection"); 
if (connection == null)
	System.out.println("***DB connect problem***");
%>
<%
	//generate arraylist of pairs containig the userID and the username of all users
	//that are currently in the system
	ArrayList<Pair<String,Integer>> users = User.getUsers(connection);

	XStream xstream = new XStream();
	
	//serialize and return
	String xml = xstream.toXML(users);
	out.println(xml);
%>