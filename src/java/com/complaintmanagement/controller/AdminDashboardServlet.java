/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.complaintmanagement.controller;

import com.complaintmanagement.dao.ComplaintDAO;
import com.complaintmanagement.dao.UserDAO;
import com.complaintmanagement.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {

    private final ComplaintDAO complaintDAO = new ComplaintDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"admin".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 1. Browser Caching Fix
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
        response.setHeader("Pragma", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        
        try {
            // 2. Fetch ALL required counts from the database
            int newRegCount = userDAO.getNewRegistrationsCount();
            int newCompCount = complaintDAO.getNewComplaintCount();
            int pendingCount = complaintDAO.getComplaintCountByStatus("Pending");
            int inProgressCount = complaintDAO.getComplaintCountByStatus("In Progress");
            int resolvedCount = complaintDAO.getComplaintCountByStatus("Resolved");
            
            // 3. Set all counts as request attributes
            request.setAttribute("registrationCount", newRegCount);
            request.setAttribute("newComplaintCount", newCompCount);
            request.setAttribute("pendingCount", pendingCount);
            request.setAttribute("inProgressCount", inProgressCount);
            request.setAttribute("resolvedCount", resolvedCount);
            
            // 4. Forward to the JSP
            request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error fetching dashboard statistics.");
            request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);
        }
    }
    
    // Ensure doPost is also handled if necessary (e.g., if a form is submitted to this URL)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}