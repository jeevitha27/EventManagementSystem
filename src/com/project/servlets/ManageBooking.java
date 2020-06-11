package com.project.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.beans.BookingInfo;
import com.project.beans.Photography;
import com.project.dbconnect.DatabaseConnect;

/**
 * Servlet implementation class ManageBooking
 */
@WebServlet("/manageBooking")
public class ManageBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageBooking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String formType = request.getParameter("formType");
		
		HttpSession session=request.getSession(); 
		
		String userIDStr =  (String) session.getAttribute("userID");
		
		DatabaseConnect dbConn = new DatabaseConnect();
		
		
		String nextPage = "/pages/BookEvent.jsp";
		
		if(formType.equalsIgnoreCase("bookEvent")){
			
			int userID = Integer.parseInt(userIDStr);
			
			BookingInfo bookingInfo = new BookingInfo();
			float totalBillingAmount = 0;
			
			int bookingID = dbConn.getNextBookingID();
			
			String eventType = request.getParameter("eventType");
			String venueName = request.getParameter("venueName");
			String noofguests = request.getParameter("noofguests");
			String eventStartDate = request.getParameter("eventStartDate");
			String eventEndDate = request.getParameter("eventEndDate");
			String photograph = request.getParameter("photograph");
			String[] equipmentsCheckBox = request.getParameterValues("equipmentsCheckBox");
			String[] foodCheckBox = request.getParameterValues("foodCheckBox");
			
			
			Calendar cal1 = new GregorianCalendar();
		    Calendar cal2 = new GregorianCalendar();

		    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    
		    try {
		    	 
		     Date date = sdf.parse(eventStartDate);
		     cal1.setTime(date);
		     date = sdf.parse(eventEndDate);
		     cal2.setTime(date);
		     
			} catch (ParseException e) {
				System.out.println(e.getLocalizedMessage());
			}
		     

		    int noOfDays = 1+daysBetween(cal1.getTime(),cal2.getTime());
		    
		    totalBillingAmount = totalBillingAmount + Float.parseFloat(eventType.split(";")[2]);
		    totalBillingAmount = totalBillingAmount + Float.parseFloat(venueName.split(";")[2]);
		    totalBillingAmount = totalBillingAmount + Float.parseFloat(photograph.split(";")[2]);
		    

		  	System.out.println("eventType-"+eventType);
			System.out.println("venueName-"+venueName);
			System.out.println("noofguests-"+noofguests);
			System.out.println("eventStartDate-"+eventStartDate);
			System.out.println("eventEndDate-"+eventEndDate);
			System.out.println("photograph-"+photograph);
			
			if(equipmentsCheckBox!=null) {
				for(int i=0;i<equipmentsCheckBox.length;i++) {
					System.out.println("Equipment-"+equipmentsCheckBox[i]);
					totalBillingAmount = totalBillingAmount + Float.parseFloat(equipmentsCheckBox[i].split(";")[2]);
					
				}
			}else {
				equipmentsCheckBox = new String[0];
			}
			
			if(foodCheckBox!=null) {
				for(int i=0;i<foodCheckBox.length;i++) {
					System.out.println("Food-"+foodCheckBox[i]);
					totalBillingAmount = totalBillingAmount + ( Float.parseFloat(foodCheckBox[i].split(";")[3]) * Float.parseFloat(noofguests));
				}
			}else {
				foodCheckBox = new String[0];
			}
			
			bookingInfo.setUserID(userID); 
			bookingInfo.setBookingID(bookingID);
			bookingInfo.setEventType(eventType);
			bookingInfo.setVenueName(venueName);
			bookingInfo.setNoofguests(noofguests);
			bookingInfo.setEventStartDate(eventStartDate);
			bookingInfo.setEventEndDate(eventEndDate);
			bookingInfo.setPhotograph(photograph);
			bookingInfo.setEquipments(equipmentsCheckBox);
			bookingInfo.setFoods(foodCheckBox);
			bookingInfo.setNoOfBookingDays(noOfDays);
			
			totalBillingAmount = totalBillingAmount * noOfDays;
			
			bookingInfo.setTotalBillingAmount(totalBillingAmount); 
			
			session.setAttribute("bookingInfo", bookingInfo);
			
		}else if(formType.equalsIgnoreCase("confirmBookEvent")){
			
			int userID = Integer.parseInt(userIDStr);
			
			BookingInfo bookingInfo = (BookingInfo) session.getAttribute("bookingInfo");
			
			String buttonPressed = request.getParameter("buttonPressed");
			
			if(buttonPressed.equalsIgnoreCase("Confirm")) {

				String cardNumber = request.getParameter("cardNumber");
				String cvvNumber = request.getParameter("cvvNumber");
				String nameOnCard = request.getParameter("nameOnCard");
				String cardExpiration = request.getParameter("cardExpiration");
				
				if(isCardValid(cardNumber,cvvNumber,nameOnCard,cardExpiration)) {
					
					String eventID = bookingInfo.getEventType().split(";")[0];
					String venueID = bookingInfo.getVenueName().split(";")[0];
					String noofguests = bookingInfo.getNoofguests();
					String eventStartDate = bookingInfo.getEventStartDate();
					String eventEndDate = bookingInfo.getEventEndDate();
					String photographyID = bookingInfo.getPhotograph().split(";")[0];
					String[] equipmentsCheckBox = bookingInfo.getEquipments();
					String[] foodCheckBox = bookingInfo.getFoods();
					
					String equipmentID = "";
					for(int i=0;i<equipmentsCheckBox.length;i++) {
						equipmentID = equipmentID + equipmentsCheckBox[i].split(";")[0] + ",";
					}
					
					if(equipmentID.length()!=0)
					equipmentID = equipmentID.substring(0,equipmentID.length()-1);
					
					String foodID = "";
					for(int i=0;i<foodCheckBox.length;i++) {
						foodID = foodID + foodCheckBox[i].split(";")[0] + ",";
					}
					
					if(foodID.length()!=0)
					foodID = foodID.substring(0,foodID.length()-1);
					
					int numberOfRowsInserted = dbConn.insertBookingInfo(userID,bookingInfo.getBookingID(),eventID,venueID,photographyID,equipmentID,foodID,noofguests,eventStartDate,eventEndDate,bookingInfo.getTotalBillingAmount());
					
					String msg=" ";
					if (numberOfRowsInserted==0) {
						msg="not inserted";
					}else {
						msg="booking is confirmed. Your Booking ID is "+bookingInfo.getBookingID();
					}
					request.setAttribute("msg", msg);
					nextPage = "/pages/ConfirmBooking.jsp";
					System.out.println("Booking Confirmed, Booking ID is "+bookingInfo.getBookingID()+", Booking done by "+userID);
					
				}else {
					request.setAttribute("errorMessage", "Card Details are not valid. Please login again and try"); 
					session.setAttribute("bookingInfo", null);
					nextPage = "/pages/LoginHome.jsp";
				}

			}else {
				
				session.setAttribute("bookingInfo", null);
				request.setAttribute("errorMessage", "Transaction is cancelled. Please login again and try"); 
				nextPage = "/pages/LoginHome.jsp";
			}
			
			
		}else if(formType.equalsIgnoreCase("getMetrics")){
			
			String eventStartDate = request.getParameter("eventStartDate");
			String eventEndDate = request.getParameter("eventEndDate");
			
			Calendar cal1 = new GregorianCalendar();
		    Calendar cal2 = new GregorianCalendar();

		    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			    
		    try {
		    	 
		     Date date = sdf.parse(eventStartDate);
		     cal1.setTime(date);
		     date = sdf.parse(eventEndDate);
		     cal2.setTime(date);
		     
			} catch (ParseException e) {
				System.out.println(e.getLocalizedMessage());
			}
		    
		    String startDate = sdf2.format(cal1.getTime());
		    String endDate = sdf2.format(cal2.getTime());
		    
		    
		    ////////////////////////
		    
		    
			float totalBookingAmount = dbConn.getBookingAmount(startDate, endDate);
			ArrayList<String> equipmentMetrics = dbConn.getEquipmentMetrics(startDate, endDate);
			ArrayList<String> foodMetrics = dbConn.getFoodMetrics(startDate, endDate);
			//ArrayList<String> eventMetrics = dbConn.getEventMetrics(startDate, endDate);
			//ArrayList<String> venueMetrics = dbConn.getVenueMetrics(startDate, endDate);
			//ArrayList<String> photoMetrics = dbConn.getPhotoMetrics(startDate, endDate);
			
			
			Double amount = (double) totalBookingAmount; 
			
			request.setAttribute("equipmentMetrics", equipmentMetrics);
			request.setAttribute("foodMetrics", foodMetrics); 
			//request.setAttribute("eventMetrics", equipmentMetrics);
			//request.setAttribute("venueMetrics", foodMetrics); 
			//request.setAttribute("photoMetrics", equipmentMetrics);
			
			request.setAttribute("totalBookingAmount", amount); 
			request.setAttribute("startDate", startDate); 
			request.setAttribute("endDate", endDate); 
			
			nextPage = "/pages/ViewMetrics.jsp";
			
			
		}
		
		
		
		request.getRequestDispatcher(nextPage).forward(request, response);
	}
	
	private boolean isCardValid(String cardNumber, String cvvNumber, String nameOnCard, String cardExpiration) {
		
		try {
			String last3Chars = cardNumber.substring(13,cardNumber.length()); 
			
			if(cvvNumber.equalsIgnoreCase(last3Chars)) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}

	public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	

}
