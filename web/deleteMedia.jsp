<%
/**
* This will take an http request containing a media ID and will delete it
* from the database
*/
%>
<%@ page import="java.sql.*,edu.nku.cs.csc440.team2.mediaCloud.*" %>
<%
Connection connection = (Connection)application.getAttribute("dbconnection"); 
if (connection == null)
	System.out.println("***DB connect problem***");
%>
<%
	String mediaId = request.getParameter("mediaId");		//ID of media to be deleted
	if (mediaId != null) {
		
		//Delete the media and return good on success
		if (Media.deleteMedia(connection, mediaId))
			out.println("1");
		else
			out.println("0");
	}
%>