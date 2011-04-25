<%
/**
* This will return an arraylist of MessageLite objects for all
* of the messages that have been sent to a specified user id
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
	String userId = request.getParameter("user");		//user ID for the requested message inbox
	if (userId != null) {
		
		//generate an ArrayList of MessageLite objects for all of the messages that have
		//been sent to the given user
		ArrayList<MessageLite> inbox = MessageLite.getMessageList(connection,Integer.parseInt(userId));
		XStream xstream = new XStream();
		
		//serialize and return
		String xml = xstream.toXML(inbox);
		out.println(xml);
	}
%>