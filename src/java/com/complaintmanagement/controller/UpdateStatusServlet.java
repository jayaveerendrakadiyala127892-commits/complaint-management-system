/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.complaintmanagement.controller;

import com.complaintmanagement.dao.ComplaintDAO;
import com.complaintmanagement.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateStatus")
public class UpdateStatusServlet extends HttpServlet {
    private ComplaintDAO complaintDAO = new ComplaintDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        User u = (User) session.getAttribute("user");
        if (!"admin".equalsIgnoreCase(u.getRole())) {
            resp.sendRedirect("login.jsp");
            return;
        }

        int complaintId = Integer.parseInt(req.getParameter("complaintId"));
        String status = req.getParameter("status");
        boolean ok = complaintDAO.updateStatus(complaintId, status);
        resp.sendRedirect("adminDashboard.jsp" + (ok ? "?msg=updated" : "?error=1"));
    }
}