/**
 * User Class
 * Data structure for users with modification methods to retrievers users in the system and also add new ones
 */

package edu.nku.cs.csc440.team2.mediaCloud;

import java.sql.*;
import java.util.ArrayList;

public class User {

	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	
	/** Retrieve first name */
	public String getFirstName() {
		return firstName;
	}
	
	/** Set first name */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/** get last name */
	public String getLastName() {
		return lastName;
	}
	
	/** Set last name */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/** get user name */
	public String getUserName() {
		return userName;
	}
	
	/** Set user name */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/** Set password*/
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * This will determine if a user has submitted a valid user/pass combination
	 * @param conn - connection to database
	 * @return
	 */
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
	
	/**
	 * This will return a pair of userNames and userIDs for every user in the system 
	 * @param conn - connection to database
	 * @return
	 */
	public static ArrayList<Pair<String,Integer>> getUsers(Connection conn) {
		ArrayList<Pair<String,Integer>> users = new ArrayList<Pair<String,Integer>>();
		try{
			Statement stmt = null;	
			ResultSet rs = null;
			stmt = conn.createStatement();
			String query = "SELECT * FROM users";
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Pair<String,Integer> temp = new Pair<String,Integer>(rs.getString("user_name"),(Integer)rs.getInt("user_id"));
				users.add(temp);
			}					
			stmt.close();
			stmt = null;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return users;		
	}
	
	
	/**
	 * This will return a userId for a given user
	 * @param conn - connection to database
	 * @return
	 */
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
	
	
	/**
	 * This will add a user object to the database
	 * @param conn - connection to database.
	 */
	public void addUser(Connection conn){
		try{
			Statement stmt = null;
			stmt = conn.createStatement();
			String query = "INSERT INTO users(first_name,last_name,user_name,password) VALUES('" + firstName + "' , '" + lastName + "' , '" + userName + "' , MD5('" + password + "'))";
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
	
