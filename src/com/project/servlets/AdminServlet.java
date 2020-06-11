package com.project.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.project.dbconnect.DatabaseConnect;

/**
 * Servlet implementation class admin
 */
@WebServlet("/admin")
@MultipartConfig(maxFileSize = 16177215)  // upload file's size up to 16MB
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
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
		System.out.println("In Add Venue");
		String formType = request.getParameter("formType");
		
		String nextPage = "/pages/loginHome.jsp";
		
		DatabaseConnect dbConn = new DatabaseConnect();
		
		if(formType.equalsIgnoreCase("AddVenue")) {
		
			String venueName = request.getParameter("venueName");
			String venueAddress = request.getParameter("venueAddress");
			String venuePhNo = request.getParameter("venuePhNo");
			
			System.out.println("venueName"+request.getParameter("venueName"));
			
			String capacity=request.getParameter("capacity");
			//String eventType = request.getParameter("eventType");
			String price=request.getParameter("price");
			 InputStream inputStream = null; // input stream of the upload file
			 
			 System.out.println(price);
		        // obtains the upload file part in this multipart request
		        Part filePart = request.getPart("photo");
		       
		        if (filePart != null) {
		            // prints out some information for debugging
		        	     
		            System.out.println(filePart.getName());
		            System.out.println(filePart.getSize());
		            System.out.println(filePart.getContentType());
		             
		            // obtains input stream of the upload file
		            inputStream = filePart.getInputStream();
		        }
			
			
			
			System.out.println(venueName +" "+venueAddress+" "+venuePhNo+" "+filePart+" "+capacity+" "+price+" "+filePart);
			
			int rows = dbConn.AddVenue(venueName,venueAddress,venuePhNo,capacity,price,inputStream);
			request.setAttribute("addedRows", rows);
			nextPage = "/pages/AddVenue.jsp";
		}
		else {
			request.setAttribute("errorMessage", "Venue cannot be added");
		}
		
		
		
		request.getRequestDispatcher(nextPage).forward(request, response);
	}

}
