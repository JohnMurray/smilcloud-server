<%
/** This will take a given message and store
* it into the users stored messages database
* Input: message/userId/name
* Returns: the message's uniqueId
*/
%>
<%@ page import="java.sql.*,java.security.MessageDigest,java.lang.String" %>
<% 
	String message = request.getParameter("message");			//Message XML
	String userId = request.getParameter("userId");				//User ID of where to save the message
	String name = request.getParameter("name");					//name of the message

	Connection connection = (Connection)application.getAttribute("dbconnection"); 
	if (connection == null)
		System.out.println("***DB connect problem***");
	
	try{
		
		//Generate a unique ID for the message
		String cTime = System.currentTimeMillis() + "";		
   	 	MessageDigest md = MessageDigest.getInstance("MD5");
   		byte[] msg = cTime.getBytes();
   		md.update(msg);
   		byte[] hashed = md.digest();   		
   		StringBuffer hexString = new StringBuffer();
   		for (int i=0;i<hashed.length;i++) {
   			hexString.append(Integer.toHexString(0xFF & hashed[i]));
   		}
   		String md5New = hexString.toString();
   		
   		//return the message ID
   		out.println(md5New);   		
		
		Statement stmt = null;
		stmt = connection.createStatement();
		
		//String query = "INSERT INTO storedmessages(message,sentDate,UserId,uniqueId,name) VALUES('" + message + "', now()," + userId + ",MD5('" + cTime + "'),'" + name + "')";
		String query = "INSERT INTO storedmessages(message,sentDate,UserId,uniqueId,name) VALUES('" + message + "', now()," + userId + ",'" + md5New + "','" + name + "')";
		stmt.execute(query);			
		stmt.close();
		stmt = null;
	} 
	catch (SQLException ex) {
		// handle any errors
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
	}

%>