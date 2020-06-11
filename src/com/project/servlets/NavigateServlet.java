package com.project.servlets;

import java.io.IOException;
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

import com.project.beans.*;
import com.project.dbconnect.DatabaseConnect;

/**
 * Servlet implementation class HarshaTestServlet
 */
@WebServlet("/navigate")
public class NavigateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NavigateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Navigate Get Method");
		
		String pageType = request.getParameter("pageType");
		
		String nextPage = "index.html";
		
		if(pageType.equalsIgnoreCase("login")) {
			nextPage = "/pages/LoginHome.jsp";
			
		}else if(pageType.equalsIgnoreCase("createAccount")) {
			
			DatabaseConnect dbConn = new DatabaseConnect();
			String userID = dbConn.getNextUserIDAccount();
			System.out.println("Got this user from DB Sequence "+userID);
    			
			request.setAttribute("userID", userID);
			nextPage = "/pages/CreateAccount.jsp";
			
		}else if(pageType.equalsIgnoreCase("forgotPwd")) {
			nextPage = "/pages/forgotPwd.jsp";
			
		}else if(pageType.equalsIgnoreCase("myDetails")) {
			
			HttpSession session=request.getSession();  
			String userID=(String) session.getAttribute("userID"); 
			System.out.println("Got this user from session "+userID);
			DatabaseConnect dbConn = new DatabaseConnect();
			Account acct = dbConn.getUserDetails(userID);
    			request.setAttribute("userAccount", acct);
			nextPage = "/pages/MyDetails.jsp";
		}
		else if(pageType.equalsIgnoreCase("event")) {
			
			DatabaseConnect dbConn = new DatabaseConnect();
			
			
			ArrayList<Event> eventList = dbConn.getAllEvents();
			ArrayList<Venue> venueList = dbConn.getAllVenues();
			ArrayList<Equipment> equipmentList = dbConn.getAllEquipments();
			ArrayList<Food> foodList = dbConn.getAllFoods();
			ArrayList<Photography> photoList = dbConn.getAllPhotographyList();
			
			ArrayList<String> unavailableDates = dbConn.getUnAvailableDates();
			
			//unavailableDates.add("2018-04-17");
			//unavailableDates.add("2018-04-18");
			//unavailableDates.add("2018-04-19");
			
			 HttpSession session=request.getSession();  

			String userID=(String) session.getAttribute("userID"); 
			System.out.println("Got this user from session "+userID);
			
			request.setAttribute("userID", userID);
			request.setAttribute("equipmentList", equipmentList);
			request.setAttribute("eventList", eventList);
			request.setAttribute("foodList", foodList);
			request.setAttribute("photoList", photoList);
			request.setAttribute("venueList", venueList);
			request.setAttribute("unavailableDates", unavailableDates);
			
			Date today = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		    String todayDate = sdf.format(today);  
		    request.setAttribute("todayDate", todayDate);
			
		    System.out.println("Todays Date "+todayDate);
		    
		    
			Account acct = dbConn.getUserDetails(userID);
			request.setAttribute("userAccount", acct);
			session.setAttribute("bookingInfo", null);
			
			nextPage = "/pages/BookEvent.jsp";
		}
		else if(pageType.equalsIgnoreCase("bookHistory")) {
			
			HttpSession session=request.getSession();  

			String userID=(String) session.getAttribute("userID"); 
			System.out.println("Got this user from session "+userID);
			
			DatabaseConnect dbConn = new DatabaseConnect();
			
			ArrayList<BookingInfo> bookingList = dbConn.getBookingHistory(userID);
			 
			Account acct = dbConn.getUserDetails(userID);
			request.setAttribute("userAccount", acct);
			request.setAttribute("userID", userID);
			request.setAttribute("bookingList", bookingList);
			
			nextPage = "/pages/BookHistory.jsp";
			
			if(userID.equals("admin")) {

				nextPage = "/pages/AdminBookHistory.jsp";
			}
		}
		else if(pageType.equalsIgnoreCase("viewMetrics")) {
			
			HttpSession session=request.getSession();  

			String userID=(String) session.getAttribute("userID"); 
			System.out.println("Got this user from session "+userID);
			
			if(userID.equals("admin")) {

				DatabaseConnect dbConn = new DatabaseConnect();
				Account acct = dbConn.getUserDetails(userID);
				request.setAttribute("userAccount", acct);
				request.setAttribute("userID", userID);
				nextPage = "/pages/ViewMetrics.jsp";
			}
		}
		else if(pageType.equalsIgnoreCase("addVenue")) {
			
			
			nextPage = "/pages/AddVenue.jsp";
		}
		else if(pageType.equalsIgnoreCase("viewVenues")) {
			
				
				ArrayList<Venue> venueList = null;
				ArrayList<String> eventList = new ArrayList<String>();
				eventList.add("BirthDay");
				eventList.add("Wedding");
				
				DatabaseConnect dbConn = new DatabaseConnect();
			
				venueList = dbConn.getAllVenues();
				
				request.setAttribute("venueList", venueList);
				request.setAttribute("eventList", eventList);
				
			
			nextPage = "/pages/ViewVenues.jsp";
		}
		else if(pageType.equalsIgnoreCase("test")) {
			
			
			
			ArrayList<Photography> photoList = null;
			DatabaseConnect dbConn = new DatabaseConnect();
			
			
			photoList = dbConn.getAllPhotographyList();
		
			
			 HttpSession session=request.getSession();  

			String userID=(String) session.getAttribute("userID"); 
			System.out.println("Got this user from session "+userID);
			
			
			request.setAttribute("photoList", photoList);
			
			
			Account acct = dbConn.getUserDetails(userID);
			request.setAttribute("userAccount", acct);
			nextPage = "/pages/Test.jsp";
		}
		else if (pageType.equalsIgnoreCase("userViewVenues")) {
			ArrayList<Venue> venueList = null;
			
			DatabaseConnect dbConn = new DatabaseConnect();
			System.out.println("In user view venues");
			venueList = dbConn.getAllVenues();
			
			request.setAttribute("venueList", venueList);
		
		
		nextPage = "/pages/UserViewVenues.jsp";
		}
		else if(pageType.equalsIgnoreCase("gallery")) {
			
			
			nextPage = "/pages/Gallery.jsp";
		}
		else if(pageType.equalsIgnoreCase("feedback")) {
			
			
			nextPage = "/pages/Feedback.jsp";
		}
		request.getRequestDispatcher(nextPage).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

}
