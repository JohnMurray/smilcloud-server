<?xml version="1.0"?>

<%@ page import="java.sql.*,mediaCloud.*,java.security.MessageDigest,java.lang.String,org.apache.commons.fileupload.*, org.apache.commons.fileupload.servlet.ServletFileUpload, org.apache.commons.fileupload.disk.DiskFileItemFactory, org.apache.commons.io.*, java.util.*, java.io.File, java.lang.Exception" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>File Upload</title>
  </head>

<body>
 <p>

<%

String name = "";
String userId = "";
String type = "";
if (ServletFileUpload.isMultipartContent(request)){
	ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
	List fileItemsList = servletFileUpload.parseRequest(request);
	String optionalFileName = "";
	FileItem fileItem = null;	
	Iterator it = fileItemsList.iterator();
	while (it.hasNext()){
    	FileItem fileItemTemp = (FileItem)it.next();
   	 if (fileItemTemp.isFormField()){
   		 
   		 
   		 if (fileItemTemp.getFieldName().equals("name")) {
   			 name = fileItemTemp.getString();
   		 }
   		 
   		 if (fileItemTemp.getFieldName().equals("user")) {
   			 userId = fileItemTemp.getString();
   		 }
   		 
   		if (fileItemTemp.getFieldName().equals("type")) {
  			 type = fileItemTemp.getString();
  		 }
   		 
		 if (fileItemTemp.getFieldName().equals("filename"))
       		optionalFileName = fileItemTemp.getString();
    }
   	else
      fileItem = fileItemTemp;
  }

  if (fileItem!=null){
    String fileName = fileItem.getName();
    if (fileItem.getSize() > 0){
   		String fExt = fileName.substring(fileName.lastIndexOf("."),fileName.length());
   		fileName = System.currentTimeMillis() + "";
   	 	MessageDigest md = MessageDigest.getInstance("MD5");
   		byte[] msg = fileName.getBytes();
   		md.update(msg);
   		byte[] hashed = md.digest();   		
   		StringBuffer hexString = new StringBuffer();
   		for (int i=0;i<hashed.length;i++) {
   			hexString.append(Integer.toHexString(0xFF & hashed[i]));
   		}   		
   		fileName = hexString.toString() + fExt;
   		
   		if (type.equals("video")) {
   			//If it's a video we are going to want to store in the streaming Wowza server root
   	    	String dirName = "/content/";
   	    	File saveTo = new File(dirName + fileName);
   	    	File test = new File(config.getServletContext().getRealPath("/")+"content\\"+fileName);
   	    	try {
   	       		fileItem.write(test);
   				out.println("Upload good");
   	      }
   			catch (Exception e){
   				out.println(e.getMessage());
   	      }   	
   			
   		}
   		else {
   			//we want to store everything else in the tomcat web root
   			
   	    	String dirName = "/content/";
   	    	File saveTo = new File(dirName + fileName);
   	    	File test = new File(config.getServletContext().getRealPath("/")+"content\\"+fileName);
   	    	try {
   	       		fileItem.write(test);
   				out.println("Upload good");
   	      }
   			catch (Exception e){
   				out.println(e.getMessage());
   	      }   			
   		}
   		

    }
    Connection connection = (Connection)application.getAttribute("dbconnection"); 
    if (connection == null)
    	System.out.println("***DB connect problem***");
    
    Media temp = new Media("content/" + fileName, "", name);
    temp.setType(type);
    temp.store(connection,Integer.parseInt(userId)); 
    
  }
}
%>

    </p>
  </body>
</html>