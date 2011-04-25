/**
 * Media Class
 * Data structure for Media stored on the server.  Can modify library by adding/deleting and also retrieve all of
 * the media that a user has uploaded.
 */


package edu.nku.cs.csc440.team2.mediaCloud;

import java.sql.*;
import java.util.ArrayList;

public class Media {
	
	private String mediaUrl;
	private String thumbUrl;
	private String name;
	private String type;
	private String mediaId;
	private String duration;
	
	public Media(String mediaUrl, String thumbUrl, String name) {
		this.mediaUrl = mediaUrl;
		this.thumbUrl = thumbUrl;
		this.name = name;		
	}
	
	/** Stores a Media object to the database */
	public void store(Connection conn, int userId) {
		this.addMedia(conn,mediaUrl,thumbUrl,name,type,userId,duration);
	}
	
	/** get the ID of the media object */
	public String getMediaId() {
		return mediaId;
	}
	
	/** get the duration of the media */
	public String getDuration() {
		return duration;
	}
	
	/** set the duration of the media */
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	/** set the id of the media */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	/** get the media filename */
	public String getMediaUrl() {
		return mediaUrl;
	}
	
	/** set the media filename */
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}
	
	/** get the thumbnail location */
	public String getThumbUrl() {
		return thumbUrl;
	}
	
	/** set the thumbnail location */	
	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}
	
	/** get the name of the media */
	public String getName() {
		return name;
	}
	
	/** set the name for the media */
	public void setName(String name) {
		this.name = name;
	}
	
	/** get the media type.  Either audio/image/video/text */
	public String getType() {
		return type;
	}
	
	/** set the type of the media.  Either audio/image/video/text */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * This method will add a media entry to the database, storing information about a media file on the server
	 * @param conn - connection to database
	 * @param mediaUrl - the filename of the media
	 * @param thumbUrl - if the media is an image or video - the filename of the thumbnail file
	 * @param name - name of the media object
	 * @param type - audio/image/text/video
	 * @param userId - user id of the user who uploaded the media
	 * @param duration - if audio or video - the length of the media
	 */
	
	private void addMedia(Connection conn, String mediaUrl, String thumbUrl, String name, String type, int userId, String duration) {		
		try{			
			String sql = "INSERT INTO media(thumbUrl, name, mediaUrl, type, userId, duration) VALUES (?, ?,?, ?, ?, ?)";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, thumbUrl);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, mediaUrl);
			preparedStatement.setString(4, type);
			preparedStatement.setInt(5, userId);
			preparedStatement.setString(6, duration);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = null;

			
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	
	/**
	 * This method will delete a media entry in the database
	 * @param conn - connection to database
	 * @param mediaId - media id primary key of media to be deleted
	 * @return	boolean of wether the media was deleted succesfully
	 */
	public static boolean deleteMedia(Connection conn, String mediaId) {
		try{			
			String sql = "DELETE FROM media WHERE mediaId = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(mediaId));
			preparedStatement.executeUpdate();
			preparedStatement.close();
			preparedStatement = null;
						
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
		return true;
	}
	
	/**
	 * This will generate an array of Media objects for all media uploaded by the specified user
	 * @param conn - connection to database
	 * @param userId - user ID of the person
	 * @return
	 */
	public static Media[] getLibrary(Connection conn, int userId) {
		ArrayList<Media> allMedia = new ArrayList<Media>();
		try{
			Statement stmt = null;	
			ResultSet rs = null;
			stmt = conn.createStatement();
			String query = "SELECT * FROM media WHERE userId = " + userId;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Media temp = new Media(rs.getString("mediaUrl"),rs.getString("thumbUrl"),rs.getString("name"));
				temp.setType(rs.getString("type"));
				temp.setDuration(rs.getString("duration"));
				temp.setMediaId(rs.getString("mediaId"));
				allMedia.add(temp);
			}			
			
			stmt.close();
			stmt = null;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		
		Media[] mediaArray = new Media[allMedia.size()];
		return allMedia.toArray(mediaArray);
	}

}
