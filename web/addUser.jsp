<%@ page import="java.sql.*" %>
<% 
	User user = new User();
    user.setFirstName(request.getParameter("firstName"));
	user.setLastName(request.getParameter("lastName"));
	user.setUserName(request.getParameter("userName"));
	user.setPassword(request.getParameter("password"));

	Connection connection = (Connection)application.getAttribute("dbconnection"); 
	if (connection == null)
		System.out.println("***DB connect problem***");
	user.addUser(connection); 
%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Registration Successful</title>
</head>

<body>
</body>

</html>