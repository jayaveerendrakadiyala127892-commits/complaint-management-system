/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.complaintmanagement.controller;

import com.complaintmanagement.dao.ComplaintDAO;
import com.complaintmanagement.model.Complaint;
import com.complaintmanagement.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@WebServlet("/viewComplaints")
public class ViewComplaintServlet extends HttpServlet {
    private ComplaintDAO complaintDAO = new ComplaintDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        User u = (User) session.getAttribute("user");

        List<Complaint> complaints;
        if ("admin".equalsIgnoreCase(u.getRole())) {
            complaints = complaintDAO.getAllComplaints();
            req.setAttribute("all", true);
        } else {
            complaints = complaintDAO.getComplaintsByUserId(u.getId());
            req.setAttribute("all", false);
        }
        req.setAttribute("complaints", complaints);
        req.getRequestDispatcher("viewComplaints.jsp").forward(req, resp);
    }
}