package com.project.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.beans.Account;
import com.project.dbconnect.DatabaseConnect;

import javax.servlet.http.*;
import java.io.*;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/authenticate")
public class AuthenticateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticateServlet() {
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
        
		System.out.println("In Login doPost");
		
		String userID=request.getParameter("userID");  
        String userPassword=request.getParameter("userPassword");  
        String nextPage = "/pages/invalid.jsp";
        
        HttpSession session=request.getSession();  

        DatabaseConnect dbConn = new DatabaseConnect();
		
        String passwordFromDB = dbConn.getPasswordFromDB(userID);
        
        System.out.println(userPassword);
        System.out.println(passwordFromDB);
        
        if(userID.equals("admin") && userPassword.equals("admin")) {
        	
        		System.out.println("Admin Validated");
        		
        		Account userAccount = dbConn.getUserDetails(userID);
        		
        		session.setAttribute("userID", userID);
        		session.setAttribute("userAccount", userAccount);
        		
        		nextPage = "/pages/AddVenue.jsp";
        		
        }else if(passwordFromDB!=null && userPassword.equals(passwordFromDB)){  
        		
        		System.out.println("User Validated");
        		
        		Account userAccount = dbConn.getUserDetails(userID);
        		
        		session.setAttribute("userID", userID);
        		session.setAttribute("userAccount", userAccount);
        		
        		nextPage = "/pages/MyDetails.jsp";
        	}else {
        		System.out.println("User ID / password not valid");
        		request.setAttribute("errorMessage", "Invalid user or password");
        		nextPage = "/pages/LoginHome.jsp";
        	}
        
        request.getRequestDispatcher(nextPage).include(request, response);  
        
	}

}
