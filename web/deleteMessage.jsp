<%
/**
* This will take a unique id for a message in an http request and 
* delete it from the database
* Returns: 1 on success, 0 on failure
*/
%>
<%@ page import="java.sql.*,edu.nku.cs.csc440.team2.mediaCloud.*" %>
<%
Connection connection = (Connection)application.getAttribute("dbconnection"); 
if (connection == null)
	System.out.println("***DB connect problem***");
%>
<%
	String messageId = request.getParameter("messageId");		//ID of message to be deleted
	if (messageId != null) {
		//Delete the message
		if (Message.deleteMessage(connection, messageId))
			out.println("1");
		else
			out.println("0");
	}
%>