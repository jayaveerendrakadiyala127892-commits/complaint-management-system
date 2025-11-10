<%@ page import="com.complaintmanagement.model.User" %>
<%@ page session="true" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Submit Complaint</title>
    <link rel="stylesheet" href="css/style_submitComplaint.css">
</head>
<body>
    
    <div class="header-nav">
        <h2>Submit New Complaint</h2>
        <div>
            <p style="margin: 0;">
                Logged in as: <strong><%= u.getName() %></strong>
                <a href="viewComplaints" class="nav-button secondary-btn">My Complaints</a>
                <a href="logout" class="nav-button primary-btn">Logout</a>
            </p>
        </div>
    </div>

    <div class="page-wrapper">
        <div class="main-content">
            
            <div class="info-card form-card">
                
                <p class="form-tagline">Please provide details regarding your issue.</p>

                <form method="post" action="submitComplaint">
                    <label for="subject">Subject:</label>
                    <input type="text" id="subject" name="subject" required>
                    
                    <label for="message">Message:</label>
                    <textarea id="message" name="message" rows="6" required></textarea>
                    
                    <button type="submit" class="submit-btn">Submit Complaint</button>
                </form>

                <% if (request.getParameter("error") != null) { %>
                    <p class="error">Failed to submit. Try again.</p>
                <% } else if ("sent".equals(request.getParameter("msg"))) { %>
                    <p class="success">Complaint submitted successfully.</p>
                <% } %>
            </div>
            
        </div>
    </div>
</body>
</html>