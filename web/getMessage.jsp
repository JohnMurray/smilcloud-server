<%
/** This will return the SMIL XML for a given message unqiue ID */
%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page language="java" contentType="text/xml"%>
<%@ page import="java.sql.*,edu.nku.cs.csc440.team2.mediaCloud.*,com.thoughtworks.xstream.XStream" %>
<%
Connection connection = (Connection)application.getAttribute("dbconnection"); 
if (connection == null)
	System.out.println("***DB connect problem***");
%>
<%
	String uniqueId = request.getParameter("uniqueId");		//ID of message to retrieve
	if (uniqueId != null) {

		//get the SMIL XML for the given message ID and return it
		String xml = Message.getMessageXMLByUID(connection,uniqueId);		
		out.println(xml);
	}
%>