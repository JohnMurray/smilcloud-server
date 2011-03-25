<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*,mediaCloud.*,com.thoughtworks.xstream.XStream" %>
<%
Connection connection = (Connection)application.getAttribute("dbconnection"); 
if (connection == null)
	System.out.println("***DB connect problem***");
%>
<%
	String userId = request.getParameter("user");
	if (userId != null) {
		Message[] inbox = Message.getAllMessages(connection, Integer.parseInt(userId));
		XStream xstream = new XStream();
		String xml = xstream.toXML(inbox);
		out.println(xml);
	}
%>