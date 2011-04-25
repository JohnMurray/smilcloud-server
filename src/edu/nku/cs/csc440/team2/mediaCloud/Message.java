/**
 * Message Class
 * This is a multi-purpose class for message modification.  It can retrieve message infomration such as title and also the SMIL XML for a given message.
 * It can also delete messages.
 */


package edu.nku.cs.csc440.team2.mediaCloud;
import java.sql.*;
import java.util.ArrayList;

public class Message {
	
	private String message;
	private String date;
	
	Message() {
	
	}
	
	Message(String message, String date) {
		this.message = message;
		this.date = date;
	}
	
	/**
	 * This will return the SMIL XML for a given message
	 * @param conn - connection to database
	 * @param uniqueId - Id of the message
	 * @return
	 */
	public static String getMessageXMLByUID(Connection conn, String uniqueId) {
		String messageXml = "";
		try{
			Statement stmt = null;	
			ResultSet rs = null;
			stmt = conn.createStatement();
			String query = "SELECT * FROM messages WHERE uniqueId = '" + uniqueId + "'";
			rs = stmt.executeQuery(query);
			
			if (!rs.next()) {
				query = "SELECT * FROM storedmessages WHERE uniqueId = '" + uniqueId + "'";
				rs = stmt.executeQuery(query);
				rs.next();
			}
			messageXml = rs.getString("message");			
			stmt.close();
			stmt = null;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return messageXml;
	}
	
	
	/**
	 * This will return a name of a message for the given ID
	 * @param conn - connection to database
	 * @param uniqueId - ID of the message
	 * @return
	 */
	public static String getMessageTitle(Connection conn, String uniqueId) {
		String messageTitle = "";
		try{
			Statement stmt = null;	
			ResultSet rs = null;
			stmt = conn.createStatement();
			String query = "SELECT * FROM messages WHERE uniqueId = '" + uniqueId + "'";
			rs = stmt.executeQuery(query);
			
			if (!rs.next()) {
				query = "SELECT * FROM storedmessages WHERE uniqueId = '" + uniqueId + "'";
				rs = stmt.executeQuery(query);
				rs.next();
			}
			messageTitle = rs.getString("name");			
			stmt.close();
			stmt = null;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		return messageTitle;
	}
	
	/**
	 * This will return an array of Messages for all messages that a user has been sent
	 * @param conn - connection to database
	 * @param userId - user ID of person
	 * @return
	 */
	public static Message[] getAllMessages(Connection conn, int userId) {
		ArrayList<Message> inbox = new ArrayList<Message>();
		try{
			Statement stmt = null;	
			ResultSet rs = null;
			stmt = conn.createStatement();
			String query = "SELECT * FROM messages WHERE userId = " + userId;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Message temp = new Message(rs.getString("message"),rs.getString("sentDate"));
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
		
		Message[] inboxArray = new Message[inbox.size()];
		return inbox.toArray(inboxArray);
	}
	/**
	 * This will delete a message from the database
	 * @param conn - connection to database
	 * @param messageId - uniqueId of the message to be deleted
	 * @return
	 */
	public static boolean deleteMessage(Connection conn, String messageId) {
		try{			
			String sql = "DELETE FROM messages WHERE uniqueId = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1,messageId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = null;
			
			String sql2 = "DELETE FROM storedmessages WHERE uniqueId = ?";
			PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
			preparedStatement2.setString(1, messageId);
			preparedStatement2.executeUpdate();
			preparedStatement2.close();
			preparedStatement2 = null;
						
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
		return true;
	}
	

}
