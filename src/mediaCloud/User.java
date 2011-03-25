package mediaCloud;

import java.sql.*;

public class User {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean isValid(Connection conn) {
		Boolean valid = false;
		try{
			Statement stmt = null;
			ResultSet rs = null;
			stmt = conn.createStatement();
			String query = "SELECT MD5('" + password + "'), password FROM users WHERE user_name = '" + userName + "'";
			rs = stmt.executeQuery(query);
			rs.next();
			if (rs.getString(1).equals(rs.getString(2)))
				valid = true;			
			stmt.close();
			stmt = null;
		} 
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return valid;
	}
	
	public String getUserId(Connection conn) {
		String userId = "-1";
		try{
			Statement stmt = null;
			ResultSet rs = null;
			stmt = conn.createStatement();
			String query = "SELECT user_id FROM users WHERE user_name = '" + userName + "'";
			rs = stmt.executeQuery(query);
			rs.next();
			userId = rs.getString(1);			
			stmt.close();
			stmt = null;
		} 
		catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return userId;
	}
	
	public void addUser(Connection conn){
		try{
			Statement stmt = null;
			stmt = conn.createStatement();
			String query = "INSERT INTO users(first_name,last_name,user_name,password) VALUES('" + firstName + "' , '" + lastName + "' , '" + userName + "' , MD5('" + password + "')'";
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

	}
	
}
	
