<%
/**
* This will take a unique ID of a stored message and send 
* it to the specified user
*/
%>
<%@ page import="java.sql.*,edu.nku.cs.csc440.team2.mediaCloud.*" %>
<% 

	String uniqueId = request.getParameter("uniqueId");			//ID of stored message to be sent
	String userId = request.getParameter("userId");				//userID of the sender
	String recipient = request.getParameter("recipient");		//userID of who to send the message to

	Connection connection = (Connection)application.getAttribute("dbconnection"); 
	if (connection == null)
		System.out.println("***DB connect problem***");
	
	//get the message XML
	String message = Message.getMessageXMLByUID(connection,uniqueId);
	
	//get the message name
	String name = Message.getMessageTitle(connection,uniqueId);
	
	
	//send the message
	try{
		Statement stmt = null;
		stmt = connection.createStatement();
		String cTime = System.currentTimeMillis() + "";		
		String query = "INSERT INTO messages(message,sentDate,UserId,uniqueId,name,senderId) VALUES('" + message + "', now()," + recipient + ",MD5('" + cTime + "'),'" + name + "'," + userId + ")";
		stmt.execute(query);
		stmt.close();
		stmt = null;
		out.println(1);
	} 
	catch (SQLException ex) {
		// handle any errors
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
		out.println("-1");
	}

%>