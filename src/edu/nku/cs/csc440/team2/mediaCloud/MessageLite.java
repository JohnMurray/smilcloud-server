/**
 * MessageLite Class
 * This class stores metadata about messages.  It can retrieve from the database list of messages stored
 * and retrived by particular users. 
 */


package edu.nku.cs.csc440.team2.mediaCloud;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MessageLite {
	
	private String name;
	private String sender;
	private String date;
	private String uniqueId;
	
	/** Message Lite constructor */
	MessageLite(String name, String sender, String date, String uniqueId) {
		this.name = name;
		this.sender = sender;
		this.date = date;
		this.uniqueId = uniqueId;
	}
	
	/** get message name */
	public String getName() {
		return name;
	}
	
	/** get who sent the message */
	public String getSender() {
		return sender;
	}
	
	/** get sent date */
	public String getDate() {
		return date;
	}
	
	/** get the unique id of the message*/
	public String getUniqueId() {
		return uniqueId;
	}
	
	/** set message name */
	public void setName(String name) {
		this.name = name;
	}
	
	/** get message sender */
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	/** set the unique id of the message object */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	
	/**
	 * This will return an arraylist of MessageLite objects for all of the messages a user has been sent
	 * @param conn - connection to database
	 * @param userId - user id of person
	 * @return
	 */
	public static ArrayList<MessageLite> getMessageList(Connection conn, int userId) {
		ArrayList<MessageLite> inbox = new ArrayList<MessageLite>();
		try{
			Statement stmt = null;	
			ResultSet rs = null;
			stmt = conn.createStatement();
			String query = "SELECT * FROM messages WHERE userId = " + userId;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				MessageLite temp = new MessageLite(rs.getString("name"),"",rs.getString("sentDate"),rs.getString("uniqueId"));
				
				Statement stmtUser = null;	
				ResultSet rsUser = null;
				stmtUser = conn.createStatement();
				String queryUser = "SELECT * FROM users WHERE user_id = " + rs.getString("senderId");
				rsUser = stmtUser.executeQuery(queryUser);
				rsUser.next();
				temp.setSender(rsUser.getString("first_name") + " " + rsUser.getString("last_name"));
				stmtUser.close();
				stmtUser = null;						
				
				//add message to inbox arraylist
				inbox.add(temp);
			}			
			
			stmt.close();
			stmt = null;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return inbox;
	}
	
	
	/**
	 * This will return an arraylist of MessageLite objects for all messages a user has saved
	 * @param conn - connection to database
	 * @param userId - user id of person
	 * @return
	 */
	public static ArrayList<MessageLite> getStoredMessageList(Connection conn, int userId) {
		ArrayList<MessageLite> inbox = new ArrayList<MessageLite>();
		try{
			Statement stmt = null;	
			ResultSet rs = null;
			stmt = conn.createStatement();
			String query = "SELECT * FROM storedmessages WHERE userId = " + userId;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				MessageLite temp = new MessageLite(rs.getString("name"),"",rs.getString("sentDate"),rs.getString("uniqueId"));			
				
				//add message to inbox arraylist
				inbox.add(temp);
			}			
			
			stmt.close();
			stmt = null;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return inbox;
	}
	
	/**
	 * Compares two message objects
	 * @param m - input message
	 * @return
	 */
	public boolean equals(MessageLite m) {
		return (m.getName() == this.name &&
				m.getUniqueId() == this.uniqueId &&
				m.getSender() == this.sender &&
				m.getDate() == this.date);
	}

}
