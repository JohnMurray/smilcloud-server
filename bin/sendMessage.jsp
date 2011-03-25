<%@ page import="java.sql.*" %>
<% 

	String message = request.getParameter("message");
	String userId = request.getParameter("userId");

	Connection connection = (Connection)application.getAttribute("dbconnection"); 
	if (connection == null)
		System.out.println("***DB connect problem***");
	
	try{
		Statement stmt = null;
		stmt = connection.createStatement();
		String query = "INSERT INTO messages(userId,message,sentDate) VALUES('" + Integer.parseInt(userId) + "' , '" + message + "' , now())";
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
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Message Sent</title>
</head>

<body>
</body>

</html>