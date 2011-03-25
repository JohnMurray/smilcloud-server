package mediaCloud;

import java.sql.*;

import javax.servlet.*;

public class MysqlContextListener implements ServletContextListener {
	Connection conn = null;
	
	public void contextInitialized (ServletContextEvent event){
		try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (Exception ex) {
				System.out.println("*****Error loading the driver*******");
			}
			
		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/mediacloud?user=root&password=nku2009");
			ServletContext sc = event.getServletContext();
			sc.setAttribute("dbconnection", conn);
			System.out.println("*******set dbconnection attribute *******");
			
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
	}
	
	public void contextDestroyed (ServletContextEvent event){
		try{
			conn.close();
			conn = null;
		}catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}