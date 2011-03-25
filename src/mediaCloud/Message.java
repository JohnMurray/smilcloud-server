package mediaCloud;
import java.sql.*;
import java.util.ArrayList;

public class Message {
	
	private String message;
	private String date;
	
	Message(String message, String date) {
		this.message = message;
		this.date = date;
	}
	
	
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
	

}
