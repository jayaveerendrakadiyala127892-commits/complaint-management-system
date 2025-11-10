/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.complaintmanagement.controller;

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


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User u = userDAO.validateUser(email, password);
        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", u);
            if ("admin".equalsIgnoreCase(u.getRole())) {
                resp.sendRedirect("adminDashboard.jsp");
            } else {
                resp.sendRedirect("submitComplaint.jsp");
            }
        } else {
            resp.sendRedirect("login.jsp?error=1");
        }
    }
}