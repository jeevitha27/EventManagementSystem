package com.project.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.project.dbconnect.DatabaseConnect;
import com.project.beans.*;
import com.project.dbconnect.DatabaseConnect;


/**
 * Servlet implementation class ManageEvents
 */
@WebServlet("/manageEvent")
public class ManageEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageEvents() {
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
		// TODO Auto-generated method stub
		String formType = request.getParameter("formType");
		
		DatabaseConnect dbConn = new DatabaseConnect();
		ArrayList<Photography> eventList = new ArrayList<Photography>();
		
		
		
		String nextPage = "/pages/login.jsp";
		if(formType.equalsIgnoreCase("SearchEvent"))
		{
			String [] checkedBoxNumber = request.getParameterValues("checkedBox");
		for(int i=0;i<checkedBoxNumber.length;i++) {
			System.out.println(checkedBoxNumber[i]);
			
			String eventName = request.getParameter("eventName"+checkedBoxNumber[i]);
			float eventPrice = Float.parseFloat(request.getParameter("eventPrice"+checkedBoxNumber[i]));
			System.out.println(eventName);
			System.out.println(eventPrice);
			
			Photography photo = new Photography();
			photo.setPhotoName(eventName);
			photo.setPhotoPrice(eventPrice);
			eventList.add(photo);

			}
		
			dbConn.insertSelectedEvents(eventList);
		}
		else if(formType.equalsIgnoreCase("Test")) {
			String [] checkedBoxNumber = request.getParameterValues("checkedBox");
			for(int i=0;i<checkedBoxNumber.length;i++) {
				System.out.println(checkedBoxNumber[i]);
			}
		}
		else if(formType.equalsIgnoreCase("UpdateOrDeleteVenue")) {
			
			System.out.println("Inside UpdateOrDeleteVenue");
			
				String buttonPressed = request.getParameter("buttonPressed");
				
			System.out.println("Button pressed "+buttonPressed);
			
				String [] selectType = request.getParameterValues("checkedBox");
				
			System.out.println("Number of checkbox selected "+selectType.length); 
				
				ArrayList<Venue> venueList = new ArrayList<Venue>();
				
				if(selectType != null) {
				if(buttonPressed.equalsIgnoreCase("Update")) {
					
				  for(int i=0;i<selectType.length;i++) {
					  
					int tmpCount = Integer.parseInt(selectType[i]);
					
					Venue tmpVenue = new Venue();
					
					String venueID = request.getParameter("venueID"+tmpCount);
					String eventName = request.getParameter("eventName"+tmpCount);
					String eventAddress=request.getParameter("eventAddress"+tmpCount);
					String eventPhNo = request.getParameter("eventPhNo"+tmpCount);
					String eventCapacity=request.getParameter("eventCapacity"+tmpCount);
					String eventEventType=request.getParameter("eventEventType"+tmpCount);
			
					String eventPrice=request.getParameter("eventPrice"+tmpCount);
			
			
					System.out.println(venueID +" "+eventAddress+" "+eventPhNo+" "+eventEventType+" "+eventPrice);
			
					tmpVenue.setVenueID(Long.parseLong(venueID));
					tmpVenue.setVenueName(eventName);
					tmpVenue.setVenueAddress(eventAddress);
					tmpVenue.setVenuePhNo(Long.parseLong(eventPhNo));
					tmpVenue.setCapacity(Long.parseLong(eventCapacity));
					tmpVenue.setEventType(eventEventType);
					tmpVenue.setPrice(Float.parseFloat(eventPrice));
					
					venueList.add(tmpVenue);
					
				  }
				  
				  dbConn.updateMultipleVenues(venueList);
				  
					//int rows = dbConn.UpdateVenue(venueID,eventName,eventAddress,eventPhNo,eventCapacity,eventEventType,eventPrice,inputStream);
					//request.setAttribute("addedRows", rows);
					
					
				}
				else if(buttonPressed.equalsIgnoreCase("Delete")) {
					
					System.out.println("In delete Venue");
					//String venueID = request.getParameter("venueID");
					
					for(int i=0;i<selectType.length;i++) {
						  
						int tmpCount = Integer.parseInt(selectType[i]);
						
						Venue tmpVenue = new Venue();
						
						String venueID = request.getParameter("venueID"+tmpCount);
						
						tmpVenue.setVenueID(Long.parseLong(venueID));
						venueList.add(tmpVenue);
						
						
						System.out.println(venueID);
						
					}
					
					int DeletedRow = dbConn.DeleteVenue(venueList);
					request.setAttribute("DeletedRow", DeletedRow);
					}
			
				}
			
				venueList = dbConn.getAllVenues();
				
				request.setAttribute("venueList", venueList);
				
				nextPage = "/pages/ViewVenues.jsp";
			}
		request.getRequestDispatcher(nextPage).forward(request, response);
	}

}
