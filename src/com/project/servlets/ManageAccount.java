package com.project.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.beans.Account;
import com.project.dbconnect.DatabaseConnect;

/**
 * Servlet implementation class MyServlet
 */
@WebServlet("/manageAccount")
public class ManageAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In doGet MyServlet");
		String formType = request.getParameter("formType");
		
		DatabaseConnect dbConn = new DatabaseConnect();
		
		String nextPage = "/pages/ListAccount.jsp";
		System.out.println(nextPage);
		
		if(formType.equalsIgnoreCase("ListAccount")) {
			
			ArrayList<Account> AccountList = dbConn.getAllAccount();
			request.setAttribute("AccountList", AccountList);
			nextPage = "/pages/ListAccount.jsp";
		}
		
		request.getRequestDispatcher(nextPage).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("In Manage Accounts");
		String formType = request.getParameter("formType");
		
		String nextPage = "/pages/LoginHome.jsp";
		
		DatabaseConnect dbConn = new DatabaseConnect();
		
		if(formType.equalsIgnoreCase("CreateAccount")) {
		
			String userID = request.getParameter("userID");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String address=request.getParameter("address");
			String mobileNo = request.getParameter("mobileNo");
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			
			System.out.println(firstName +" "+lastName);
			
			int rows = dbConn.createAccount(userID,firstName,lastName,address,mobileNo,email,password);
			request.setAttribute("addedRows", rows);
			nextPage = "/pages/LoginHome.jsp";
			
		}else if(formType.equalsIgnoreCase("forgotPwd")) {
			
			System.out.println("In Forgot Password");
			String userID=request.getParameter("newPwd"); 
			String userPassword=request.getParameter("userPassword"); 
			String verifyPassword=request.getParameter("verifypassword");
			//updating password
			int rowsUpdated = dbConn.updatePwd(userID,userPassword,verifyPassword);
			request.setAttribute("rowsUpdated", rowsUpdated);
			nextPage = "/pages/LoginHome.jsp";
		} 
		else if(formType.equalsIgnoreCase("UpdateMyDetails")) {
			
			System.out.println("In UpdateMyDetails");
			String userID = request.getParameter("userID");
			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			
			String address=request.getParameter("address");
			String mobileNo = request.getParameter("mobileno");
			String email=request.getParameter("email");
			
			int updatedRows = dbConn.UpdateMyDetails(userID,firstName,lastName,address,mobileNo,email);
			request.setAttribute("updatedRows", updatedRows);
			
			Account acct = dbConn.getUserDetails(userID);
    		
    			request.setAttribute("userAccount", acct);
    			nextPage = "/pages/MyDetails.jsp";
    			

			
		}
		else {
			request.setAttribute("errorMessage", "User ID or email not found in database");
		}
			
		
		request.getRequestDispatcher(nextPage).forward(request, response);
		
		
		
	}

}
