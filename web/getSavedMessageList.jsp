<%
/**
* This will return an arraylist of MessageLite objects for all messages
* that a user has saved
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
	String userId = request.getParameter("user");		//user id for given stored messages
	if (userId != null) {
		
		//generate arraylist of messagelite objects for all messages that the user has saved
		ArrayList<MessageLite> inbox = MessageLite.getStoredMessageList(connection,Integer.parseInt(userId));
		XStream xstream = new XStream();
		
		//serialize and return
		String xml = xstream.toXML(inbox);
		out.println(xml);
	}
%>