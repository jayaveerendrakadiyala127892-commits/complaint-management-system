<%@ page import="java.util.List" %>
<%@ page import="com.complaintmanagement.model.Complaint" %>
<%@ page import="com.complaintmanagement.model.User" %>
<%@ page session="true" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Complaint> complaints = (List<Complaint>) request.getAttribute("complaints");
    if (complaints == null) {
        response.sendRedirect("viewComplaints");
        return;
    }
    
    boolean isAdmin = "admin".equalsIgnoreCase(u.getRole());
    int colspan = isAdmin ? 6 : 5;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= isAdmin ? "All Complaints" : "My Complaints" %></title>
    <link rel="stylesheet" href="css/style_viewComplaints.css">
</head>
<body>
    
    <div class="header-nav">
        <h2><%= isAdmin ? "All Complaints" : "My Complaints" %></h2>
        <div>
            <p style="margin: 0;">Logged in as: <strong><%= u.getName() %></strong>
            <a href="submitComplaint.jsp" class="nav-button secondary-btn">Submit New</a>
            <a href="<%= isAdmin ? "adminDashboard.jsp" : "viewComplaints" %>" class="nav-button secondary-btn"><%= isAdmin ? "Dashboard" : "My Complaints" %></a> 
            <a href="logout" class="nav-button primary-btn">Logout</a></p>
        </div>
    </div>

    <div class="page-wrapper">
        <div class="main-content">

            <table class="complaints-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Subject</th>
                        <th>Message</th>
                        <th>Status</th>
                        <th>Date</th>
                        <% if (isAdmin) { %><th>Actions</th><% } %>
                    </tr>
                </thead>
                <tbody>
                <%
                    if (complaints.isEmpty()) {
                %>
                    <tr><td colspan="<%= colspan %>">No complaints found.</td></tr>
                <%
                    } else {
                        for (Complaint c : complaints) {
                %>
                    <tr>
                        <td><%= c.getId() %></td>
                        <td class="table-subject"><%= c.getSubject() %></td>
                        <td class="table-message"><%= c.getMessage() %></td>
                        <td class="status-cell">
                           <span class="status-pill status-<%= c.getStatus().replaceAll("\\s+", "-").toLowerCase() %>"><%= c.getStatus() %></span>
                        </td>
                        <td><%= c.getDate() %></td>
                        <% if (isAdmin) { %>
                        <td>
                            <form method="post" action="updateStatus" class="status-form">
                                <input type="hidden" name="complaintId" value="<%= c.getId() %>">
                                <select name="status">
                                    <option value="Pending" <%= "Pending".equals(c.getStatus())? "selected":"" %>>Pending</option>
                                    <option value="In Progress" <%= "In Progress".equals(c.getStatus())? "selected":"" %>>In Progress</option>
                                    <option value="Resolved" <%= "Resolved".equals(c.getStatus())? "selected":"" %>>Resolved</option>
                                </select>
                                <button type="submit" class="update-btn">Update</button>
                            </form>
                        </td>
                        <% } %>
                    </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
            
        </div>
    </div>
</body>
</html> 