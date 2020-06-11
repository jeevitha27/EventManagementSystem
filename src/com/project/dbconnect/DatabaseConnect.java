package com.project.dbconnect;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

import com.project.beans.Account;
import com.project.beans.BookingInfo;
import com.project.beans.Equipment;
import com.project.beans.Event;
import com.project.beans.Food;
import com.project.beans.Photography;
import com.project.beans.Venue;

public class DatabaseConnect {
	
	
private long myId;

private Connection getDbConnection() {
		
		Connection con = null;
		
		try{  
			//step1 load the driver class  
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			
			//step2 create  the connection object  
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl","jeeva","oracle");  

			//String sts = new String();
			
		}catch(Exception e){ 
			e.printStackTrace();
		}  

		return con;
	}
	
	public int createAccount(String userID,String firstName,String lastName,String address,String mobileNo,String email,String password) {
		
		int rowsAdded = 0;
		try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  

			System.out.println("Inserting records into the EVENT_ACCOUNT table...");
			  
			
		      String sql = "INSERT INTO EVENT_ACCOUNT (USERID,FIRSTNAME,LASTNAME,ADDRESS,MOBILENO,EMAIL,PASSWORD) VALUES ('"+userID+"', '"+firstName+"', '"+lastName+"','"+address+"',"+mobileNo+",'"+email+"','"+password+"')";
		      System.out.println("Executing query "+sql);
		      
		      rowsAdded = stmt.executeUpdate(sql);
		      stmt.executeUpdate("commit");
		      
		      System.out.println("Inserted record into the EVENT_ACCOUNT table...");
		      

			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			System.out.println(e.getLocalizedMessage()); 
		}  

		return rowsAdded;
	}

	public ArrayList<Account> getAllAccount() {
		
		ArrayList<Account> AccountList = new ArrayList<Account>();
		
		System.out.println("In getAllAccount");
		try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select userID,firstName,lastName,address,mobileNo,email from Event_Account");  
			while(rs.next())  {
				Account acc = new Account();
				acc.setUserID(rs.getString(1));
				acc.setFirstName(rs.getString(2));
				acc.setLastName(rs.getString(3));
				acc.setAddress(rs.getString(4));
				acc.setMobileNo(rs.getInt(5));
				acc.setEmail(rs.getString(6));
				
				AccountList.add(acc);
				//System.out.println(rs.getString(1)+"  "+rs.getString(2)+" "+rs.getString(3));  
			}
			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			System.out.println(e);
		}  

		return AccountList;
	}
public ArrayList<Account> getUserIDAccount() {
		
		ArrayList<Account> AccountUseID = new ArrayList<Account>();
		
		System.out.println("In getUserIDAccount");
		try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select userID from Event_Account");  
			while(rs.next())  {
				Account acc = new Account();
				acc.setUserID(rs.getString(1));
				
				
				AccountUseID.add(acc);
				//System.out.println(rs.getString(1)+"  "+rs.getString(2)+" "+rs.getString(3));  
			}
			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			System.out.println(e);
		}  

		return AccountUseID;
	}

public String getNextUserIDAccount() {
	
	String userID = null;
	System.out.println("In getUserDetails");
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		String query = "select userID.NEXTVAL from Event_Account";
		//step4 execute query  
		System.out.println(query);
		
		ResultSet rs=stmt.executeQuery(query);  
		while(rs.next())  {

			userID = rs.getString(1);
			  
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		
		System.out.println(e.getLocalizedMessage());
	}  

	return userID;
}

public int getNextBookingID() {
	
	int bookingID = 0;
	System.out.println("In getUserDetails");
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		String query = "select BOOKINGID.NEXTVAL from Event_Bookinginfo";
		//step4 execute query  
		System.out.println(query);
		
		ResultSet rs=stmt.executeQuery(query);  
		while(rs.next())  {

			bookingID = rs.getInt(1);
			  
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		
		System.out.println(e.getLocalizedMessage());
	}  

	return bookingID;
}



	public Account getUserDetails(String userID) {
		
		Account acc = new Account();
		
		System.out.println("In getUserDetails");
		try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			String query = "select userID,firstName,lastName,address,mobileNo,email from Event_Account where userid='"+userID+"'";
			//step4 execute query  
			System.out.println(query);
			
			ResultSet rs=stmt.executeQuery(query);  
			while(rs.next())  {

				acc.setUserID(rs.getString(1));
				acc.setFirstName(rs.getString(2));
				acc.setLastName(rs.getString(3));
				acc.setAddress(rs.getString(4));
				acc.setMobileNo(rs.getLong(5));
				acc.setEmail(rs.getString(6));
				
				System.out.println(rs.getString(1)+"  "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6));  
			}
			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			e.printStackTrace();
		}  

		return acc;
	}

	public String getPasswordFromDB(String userID) {
		
		String dbPassword = null;
		
		System.out.println("In getPasswordFromDB");
		try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select PASSWORD from Event_Account where userid='"+userID+"'");  
			while(rs.next())  {
				
				dbPassword = rs.getString(1);
				  
			}
			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			System.out.println(e);
		}  
		
		return dbPassword;
	}

	public int updatePwd(String userID,String userPassword,String verifyPassword) {
		
		int rowsUpdated = 0;
		try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  

			System.out.println("Updating records into the table...");
		      
		      String sql = ("UPDATE EVENT_ACCOUNT SET "
		      		+ "password=('"+userPassword+"'),verifyPassword=('"+verifyPassword+"') where "
		      	    + "userID=('"+userID+"')");
		      System.out.println("Executing query "+sql);
		      
		      rowsUpdated = stmt.executeUpdate(sql);
		      
		      System.out.println("Updated record from the table...");
		      

			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			e.printStackTrace();
		}  

		return rowsUpdated;
		
	}
	
	public int UpdateMyDetails(String userID,String firstName,String lastName,String address,String mobileNo,String email) {
	    int updatedRows=0;
		
	    try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  

			System.out.println("Updating records into the table...");
		      
		      String sql = ("UPDATE EVENT_ACCOUNT SET "
		      		+ "firstName=('"+firstName+"'),lastName=('"+lastName+"'),address=('"+address+"'),mobileNo=('"+mobileNo
		      		+"'),email=('"+email+"') where "
		      	    + "userID=('"+userID+"')");
		      System.out.println("Executing query "+sql);
		      
		      updatedRows = stmt.executeUpdate(sql);
		      
		      System.out.println("Updated record from the table...");
		      

			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			e.printStackTrace();
		}
		
		return updatedRows;
	}

	public ArrayList<Photography> getAllPhotographyList() {
		
		ArrayList<Photography> photoList = new ArrayList<Photography>();
		
		System.out.println("In getAllPhotographyList");
		try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select PHOTOGRPHYID,PHOTOGRPHYNAME,CONTACT,PHOTOGRAPHYPRICE from event_photography");  
			while(rs.next())  {
				
				Photography photo = new Photography();
				
				photo.setPhotoID(rs.getInt(1));
				photo.setPhotoName(rs.getString(2));
				photo.setContact(rs.getLong(3));
				photo.setPhotoPrice(rs.getFloat(4));
				
				photoList.add(photo);
				
				//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getLong(3)+"  "+rs.getFloat(4));  
			}
			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			System.out.println(e);
		}  
		
		//System.out.println(photoList.size());
		
		return photoList;

	}
	
	public ArrayList<Venue> getAllVenues() {
		
		ArrayList<Venue> venueList = new ArrayList<Venue>();
		
		System.out.println("In getAllVenues");
		try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  
			ResultSet rs=stmt.executeQuery("select venueID,venueName,venueAddress,venuePhNo,capacity,price from EVENT_VENUE");  
			while(rs.next())  {
				
				Venue venue = new Venue();
				
				venue.setVenueID(rs.getLong(1));
				venue.setVenueName(rs.getString(2));
				venue.setVenueAddress(rs.getString(3));
				venue.setVenuePhNo(rs.getLong(4));
				venue.setCapacity(rs.getLong(5));
				venue.setPrice(rs.getFloat(6));
				
				venueList.add(venue);
				
				System.out.println(rs.getLong(1)+"  "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getLong(4)+"  "+rs.getLong(5)+"  "+rs.getFloat(6)); 
			
			
			
			
			}
			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			System.out.println(e);
		}  
		
		//System.out.println(photoList.size());
		
		return venueList;

	}

	public void insertSelectedEvents(ArrayList<Photography> eventList) {
		
		System.out.println("Number of rows inserted = "+eventList.size());
	}
	
	
public int AddVenue(String venueName,String venueAddress,String venuePhNo,String capacity,String price,InputStream inputStream) {
		
		int rowsAdded = 0;
		try{  
			Connection con = getDbConnection();
			
			String sqlIdentifier = "select venueID.NEXTVAL from EVENT_VENUE";
			PreparedStatement pst = con.prepareStatement(sqlIdentifier);
		
			   ResultSet rs = pst.executeQuery();
			   if(rs.next())
			     myId = rs.getLong(1);
			
			String sql = "INSERT INTO Event_Venue VALUES (?,?,?,?,?,?,?)";
			
			//step3 create the statement object  
			PreparedStatement stmt=con.prepareStatement(sql);  

			//step4 execute query  

			System.out.println("Inserting records into the table...");
		      
		     
		      System.out.println("Executing query "+sql);
		      stmt.setLong(1, myId);
		      stmt.setString(2, venueName);
		      stmt.setString(3, venueAddress);
		      stmt.setString(4, venuePhNo);
		      stmt.setString(5, capacity);
		      stmt.setString(6, price);   
	            if (inputStream != null) {
	                // fetches input stream of the upload file for the blob column
	            	System.out.println("Inside Input Stream");
	                stmt.setBlob(7, inputStream);
	            }
		      //stmt.setString(8, ImageName);
		      
		      
		      
		      rowsAdded = stmt.executeUpdate();
		      
		      
		      
		      System.out.println("Inserted record into the table...");
		      

			//step5 close the connection object  
			con.close();  

				}catch(Exception e){ 
					e.printStackTrace();
				}  

		return rowsAdded;
	}
	
public byte[] getImageByVenueName(String venueName) {
	
	byte[] byteArray = null;
	
	//System.out.println("In getImageByVenueName");
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		//step4 execute query  
		ResultSet rs=stmt.executeQuery("select image from EVENT_VENUE where venuename='"+venueName+"'");  
		while(rs.next())  {
			
			
			Blob image = rs.getBlob(1);
			
			
			int blobLength;
			
			blobLength = (int) image.length();

			byteArray = image.getBytes(1, blobLength);
		
		
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e);
	}  
	
	return byteArray;

}

public int DeleteVenue(ArrayList<Venue> venueList) {
	 int deletedRow=0;
		
	    try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  

			System.out.println("Deleting records from the table...");
			
			for(Venue venue:venueList) {
		      
		      String sql = ("DELETE FROM EVENT_VENUE where venueID=('"+venue.getVenueID()+"')"); 
		      		
		      System.out.println("Executing query "+sql);
		      
		      deletedRow = stmt.executeUpdate(sql);
		      System.out.println("Deleted record from the table...");
			}
		      
		    
		      

			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			e.printStackTrace();
		}
		
		return deletedRow;

}

public int UpdateVenue(String venueID, String eventName, String eventAddress, String eventPhNo,String eventCapacity, String eventEventType,
		String eventPrice,  InputStream inputStream) {

	 int updatedRows=0;
		
	    try{  
			Connection con = getDbConnection();
			
			//step3 create the statement object  
			Statement stmt=con.createStatement();  

			//step4 execute query  

			System.out.println("Updating records into the table...");
		      
		      String sql = ("UPDATE EVENT_VENUE SET "
		      		+ "venueName=('"+eventName+"'),venueAddress=('"+eventAddress+"'),venuePhNo=('"+eventPhNo
		      		+"'),capacity=('"+eventCapacity+"'),price=('"+eventPrice+"'),image=('"+inputStream+"') where "
		      	    + "venueID=('"+venueID+"')");
		      System.out.println("Executing query "+sql);
		      
		      updatedRows = stmt.executeUpdate(sql);
		      
		      System.out.println("Updated record from the table...");
		      

			//step5 close the connection object  
			con.close();  

		}catch(Exception e){ 
			e.printStackTrace();
		}
		
		return updatedRows;
}

public void updateMultipleVenues(ArrayList<Venue> venueList) {
	
	
	
    try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		//step4 execute query  

		System.out.println("Updating records into the table...");

		for(Venue venue:venueList) {
			
			String sql = ("UPDATE EVENT_VENUE SET "
		      		+ "venueName=('"+ venue.getVenueName()+"'),venueAddress=('"+venue.getVenueAddress()+"'),venuePhNo=('"+venue.getVenuePhNo()
		      		+"'),capacity=('"+venue.getCapacity()+"'),price=('"+venue.getPrice()+"')  where "
		      	    + "venueID=('"+venue.getVenueID()+"')");
		      System.out.println("Executing query "+sql);
		      
		      stmt.executeUpdate(sql);
		}
	      
	      
	      System.out.println("Updated record from the table...");
	      

		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		e.printStackTrace();
	}
	
	
	
}

public ArrayList<Equipment> getAllEquipments() {
	
	ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();
	
	System.out.println("In getAllEquipments");
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		//step4 execute query  
		ResultSet rs=stmt.executeQuery("select EQUIPMENTID,EQUIPMENTNAME,EQUIPMENTPROVIDER,EQUIPMENTCONTACT,EQUIPMENTPRICE from Event_EQUIPMENT");  
		while(rs.next())  {
			Equipment equipment = new Equipment();
			
			equipment.setEquipmentID(rs.getInt(1));
			equipment.setEquipmentName(rs.getString(2));
			equipment.setEquipmentProvider(rs.getString(3));
			equipment.setEquipmentContact(rs.getLong(4));
			equipment.setEquipmentPrice(rs.getFloat(5));
			
			equipmentList.add(equipment);
			//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+" "+rs.getString(3)+"  "+rs.getLong(4)+" "+rs.getFloat(5));  
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e);
	}  

	return equipmentList;
	
}

public ArrayList<Event> getAllEvents() {
	
	ArrayList<Event> eventList = new ArrayList<Event>();
	
	System.out.println("In getAllEvents");
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		//step4 execute query  
		ResultSet rs=stmt.executeQuery("select EVENTID,EVENTTYPE,EVENTPRICE from Event_EVENT");  
		while(rs.next())  {
			Event event = new Event();
			
			event.setEventID(rs.getInt(1));
			event.setEventName(rs.getString(2));
			event.setEventPrice(rs.getFloat(3));
			
			eventList.add(event);
			//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+" "+rs.getFloat(3));  
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e);
	}  

	return eventList;
	
}

public ArrayList<Food> getAllFoods() {

	ArrayList<Food> foodList = new ArrayList<Food>();
	
	System.out.println("In getAllEvents");
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		//step4 execute query  
		ResultSet rs=stmt.executeQuery("select FOODID,FOODTYPE,CATEGORY,FOODPRICE from Event_FOOD");  
		while(rs.next())  {
			Food food = new Food();
			
			food.setFoodID(rs.getInt(1));
			food.setFoodType(rs.getString(2));
			food.setFoodCategory(rs.getString(3)); 
			food.setFoodPrice(rs.getFloat(4));
			 
			foodList.add(food);
			//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getFloat(4));  
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e);
	}  

	return foodList;
}

public int insertBookingInfo(int userID, int bookingID, String eventID, String venueID, String photographyID,
		String equipmentID, String foodID, String noofguests, String eventStartDate, String eventEndDate,
		float totalBillingAmount) {

	int rowsAdded = 0;
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		//step4 execute query  

		System.out.println("Inserting records into the EVENT_BOOKINGINFO table...");
		  
		
	      String sql = "INSERT INTO EVENT_BOOKINGINFO (USERID,BOOKINGID,EVENTID,VENUEID,PHOTOGRPHYID,EQUIPMENTID,FOODID,NOOFGUESTS,STARTDATE,ENDDATE,BOOKINGPRICE) "
	      		+ " VALUES ("+userID+", "+bookingID+", "+eventID+","+venueID+","+photographyID+",'"+equipmentID+"','"+foodID+"',"+noofguests+",to_Date('"+eventStartDate+" 00:00', 'mm/dd/yyyy hh24:mi'),to_date('"+eventEndDate+" 23:59', 'mm/dd/yyyy hh24:mi'),"+totalBillingAmount+")";
	      System.out.println("Executing query "+sql);
	      
	      rowsAdded = stmt.executeUpdate(sql);
	      stmt.executeUpdate("commit");
	      
	      System.out.println("Inserted record into the EVENT_BOOKINGINFO table...");
	      

		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e.getLocalizedMessage()); 
	}  

	return rowsAdded;
}

public ArrayList<String> getUnAvailableDates() {
	
ArrayList<String> dateList = new ArrayList<String>();
	
	System.out.println("In getUnAvailableDates");
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		//step4 execute query  
		ResultSet rs=stmt.executeQuery("select STARTDATE,ENDDATE from EVENT_BOOKINGINFO");  
		while(rs.next())  {
			
			Date startDate = rs.getDate(1);
			Date endDate = rs.getDate(2);
			
		
			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    
			for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
			    // Do your job here with `date`.
			    System.out.println(sdf.format(date));
			    dateList.add(sdf.format(date));
			    
			}
			
			System.out.println(rs.getDate(1)+"  "+rs.getDate(2));  
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e);
	}  

	return dateList;

}

public ArrayList<BookingInfo> getBookingHistory(String userID) {
	
	ArrayList<BookingInfo> bookingList = new ArrayList<BookingInfo>();
	TreeMap<String, String> eventMap = new TreeMap<String,String>();
	TreeMap<String, String> foodMap = new TreeMap<String,String>();
	
	System.out.println("In getAllEquipments");
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  
		
		ResultSet rs1 = stmt.executeQuery("select EQUIPMENTID,EQUIPMENTNAME,EQUIPMENTPROVIDER,EQUIPMENTCONTACT,EQUIPMENTPRICE"
				+ " from EVENT_EQUIPMENT");
		while(rs1.next()) {
			
			String key = rs1.getString(1);
			String value = rs1.getString(1)+";-;"+rs1.getString(2)+";-;"+rs1.getString(3)+";-;"+rs1.getString(4)+";-;"+rs1.getString(5);
			eventMap.put(key, value);
		}

		ResultSet rs2 = stmt.executeQuery("select FOODID,FOODTYPE,CATEGORY,FOODPRICE"
				+ " from EVENT_FOOD");
		while(rs2.next()) {
			
			String key = rs2.getString(1);
			String value = rs2.getString(1)+";-;"+rs2.getString(2)+";-;"+rs2.getString(3)+";-;"+rs2.getString(4);
			foodMap.put(key, value);
		}
		
		StringBuffer query = new StringBuffer("select BOOKINGID,EQUIPMENTID,FOODID,NOOFGUESTS,TO_CHAR(STARTDATE, 'YYYY-MM-DD HH24:MI'),TO_CHAR(ENDDATE, 'YYYY-MM-DD HH24:MI'),BOOKINGPRICE," 
				+ "USERID,FIRSTNAME,LASTNAME,MOBILENO,EMAIL,ADDRESS,EVENTTYPE,EVENTPRICE,"
				+ "VENUENAME,VENUEPHNO,VENUEADDRESS,VENUECAPACITY,VENUEPRICE," 
				+ "PHOTOGRPHYNAME,PHOTOGRAPHYCONTACT,PHOTOGRAPHYPRICE " 
				+ "from EVENT_BOOKINGINFO_VIEW " );
		
		if(!userID.equalsIgnoreCase("admin")) {
			query.append(" where USERID = "+userID);
		}
		

		System.out.println(query);
				
		//step4 execute query  
		ResultSet rs=stmt.executeQuery(query.toString());
		
		while(rs.next())  {
			BookingInfo booking = new BookingInfo();
			
			booking.setBookingID(rs.getInt(1));
			
			String[] tmpEquipment = rs.getString(2).split(",");
			String[] equipments = new String[tmpEquipment.length];
			
			for(int i=0;i<tmpEquipment.length;i++) {
				equipments[i] = eventMap.get(tmpEquipment[i]);
			}
			booking.setEquipments(equipments);
			
			String[] tmpFood = rs.getString(3).split(",");
			String[] foods = new String[tmpFood.length];
			
			for(int i=0;i<tmpFood.length;i++) {
				foods[i] = foodMap.get(tmpFood[i]);
			}
			booking.setFoods(foods);
			
			
			booking.setNoOfBookingDays(rs.getInt(4));
			booking.setEventStartDate(rs.getString(5));
			booking.setEventEndDate(rs.getString(6));
			booking.setTotalBillingAmount(rs.getFloat(7));
			booking.setUserID(rs.getInt(8));
			booking.setFirstName(rs.getString(9)); 
			booking.setLastName(rs.getString(10));
			booking.setUserMobile(rs.getString(11));
			booking.setUserEmail(rs.getString(12));
			booking.setUserAddress(rs.getString(13));
			booking.setEventType(rs.getString(14));
			booking.setEventPrice(rs.getFloat(15));
			booking.setVenueName(rs.getString(16));
			booking.setVenuePhone(rs.getString(17));
			booking.setVenueAddress(rs.getString(18));
			booking.setVenueCapacity(rs.getInt(19));
			booking.setVenuePrice(rs.getFloat(20));
			booking.setPhotographyName(rs.getString(21));
			booking.setPhotographyContact(rs.getString(22));
			booking.setPhotographyPrice(rs.getFloat(23));
			
			
			bookingList.add(booking);
			//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+" "+rs.getString(3)+"  "+rs.getLong(4)+" "+rs.getFloat(5));  
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e);
	}  
	
	return bookingList;
}

public float getBookingAmount(String startDate, String endDate) {
	
	System.out.println("In getBookingAmount");

	float bookingAmount = 0;
	
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		String query = "select sum(BOOKINGPRICE) from EVENT_BOOKINGINFO " + 
				"where STARTDATE >= to_date('"+startDate+" 00:00', 'YYYY-MM-DD HH24:MI') " + 
				"and ENDDATE <= to_date('"+endDate+" 23:59', 'YYYY-MM-DD HH24:MI')";
		
		System.out.println(query);
		
		
		//step4 execute query  
		ResultSet rs=stmt.executeQuery(query);  
		
		while(rs.next())  {
			
			bookingAmount = rs.getFloat(1) ;
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e);
	}  
	
	return bookingAmount;
}

public ArrayList<String> getEquipmentMetrics(String startDate, String endDate) {
	
	System.out.println("In getEquipmentMetrics");

	ArrayList<String> equipmentMetrics = new ArrayList<String>();
	
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		String query = "select distinct t3.EQUIPMENTNAME,count(*) from (" + 
				"SELECT distinct t1.BOOKINGID,trim(regexp_substr(t1.equipmentID, '[^,]+', 1, LEVEL)) equipments " + 
				"FROM EVENT_BOOKINGINFO t1 " + 
				"where STARTDATE >= to_date('"+startDate+" 00:00', 'YYYY-MM-DD HH24:MI') " + 
				"and ENDDATE <= to_date('"+endDate+" 23:59', 'YYYY-MM-DD HH24:MI') "+ 
				"CONNECT BY regexp_substr(t1.equipmentID , '[^,]+', 1, LEVEL) IS NOT NULL " + 
				") t2, EVENT_EQUIPMENT t3 " + 
				"where t2.equipments = t3.EQUIPMENTID " + 
				"group by t3.EQUIPMENTNAME " + 
				"order by count(*) desc" ;
				
		
		System.out.println(query);
		
		
		//step4 execute query  
		ResultSet rs=stmt.executeQuery(query);  
		
		while(rs.next())  {
			
			equipmentMetrics.add(rs.getString(1)+";-;"+rs.getInt(2)) ;
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e);
	}  
	
	return equipmentMetrics;
}

public ArrayList<String> getFoodMetrics(String startDate, String endDate) {
	
	System.out.println("In getFoodMetrics");

	ArrayList<String> foodMetrics = new ArrayList<String>();
	
	try{  
		Connection con = getDbConnection();
		
		//step3 create the statement object  
		Statement stmt=con.createStatement();  

		String query = "select distinct t3.FOODTYPE,t3.CATEGORY,count(*) from (" + 
				"SELECT distinct t1.BOOKINGID,trim(regexp_substr(t1.FOODID, '[^,]+', 1, LEVEL)) foods " + 
				"FROM EVENT_BOOKINGINFO t1 " + 
				"where STARTDATE >= to_date('"+startDate+" 00:00', 'YYYY-MM-DD HH24:MI') " + 
				"and ENDDATE <= to_date('"+endDate+" 23:59', 'YYYY-MM-DD HH24:MI') "+ 
				"CONNECT BY regexp_substr(t1.FOODID , '[^,]+', 1, LEVEL) IS NOT NULL " + 
				") t2, EVENT_FOOD t3 " + 
				"where t2.foods = t3.foodid " + 
				"group by t3.FOODTYPE,t3.CATEGORY " + 
				"order by count(*) desc, t3.FOODTYPE,t3.CATEGORY" ;
				
		
		System.out.println(query);
		
		
		//step4 execute query  
		ResultSet rs=stmt.executeQuery(query);  
		
		while(rs.next())  {
			
			foodMetrics.add(rs.getString(1)+";-;"+rs.getString(2)+";-;"+rs.getInt(3)) ;
		}
		//step5 close the connection object  
		con.close();  

	}catch(Exception e){ 
		System.out.println(e);
	}  
	
	return foodMetrics;
}
	
	
	
}
