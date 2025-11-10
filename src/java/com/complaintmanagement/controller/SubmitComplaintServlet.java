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

@WebServlet("/submitComplaint")
public class SubmitComplaintServlet extends HttpServlet {
    private ComplaintDAO complaintDAO = new ComplaintDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        User u = (User) session.getAttribute("user");

        String subject = req.getParameter("subject");
        String message = req.getParameter("message");

        Complaint c = new Complaint();
        c.setUserId(u.getId());
        c.setSubject(subject);
        c.setMessage(message);
        boolean ok = complaintDAO.submitComplaint(c);

        if (ok) resp.sendRedirect("viewComplaints.jsp?msg=sent");
        else resp.sendRedirect("submitComplaint.jsp?error=1");
    }
}
