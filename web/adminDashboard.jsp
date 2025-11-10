<%@ page import="com.complaintmanagement.model.User" %>
<%@ page session="true" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null || !"admin".equalsIgnoreCase(u.getRole())) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Assumes Controller has fetched and set these FRESH counts from the DB
    Integer regCount = (Integer) request.getAttribute("registrationCount");
    Integer newCompCount = (Integer) request.getAttribute("newComplaintCount");
    Integer pendingCount = (Integer) request.getAttribute("pendingCount");
    Integer resolvedCount = (Integer) request.getAttribute("resolvedCount");
    Integer inProgressCount = (Integer) request.getAttribute("inProgressCount");
    
    // Provide defaults for display safety
    int reg = (regCount != null) ? regCount : 0;
    int newComp = (newCompCount != null) ? newCompCount : 0;
    int pend = (pendingCount != null) ? pendingCount : 0;
    int resolved = (resolvedCount != null) ? resolvedCount : 0;
    int progress = (inProgressCount != null) ? inProgressCount : 0;

    int total = pend + resolved + progress;
    
    // Add caching headers to prevent stale data display (Browser Fix)
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
    response.setHeader("Pragma", "no-cache"); 
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="css/style_adminDashboard.css">
</head>
<body>
    
    <div class="header-nav">
        <h2>Admin Dashboard</h2>
        <div>
            <p style="margin: 0;">Logged in as: <strong><%= u.getName() %></strong>
            <a href="viewComplaints" class="nav-button secondary-btn">View Complaints</a>
            <a href="logout" class="nav-button primary-btn">Logout</a></p>
        </div>
    </div>

    <div class="page-wrapper">
        <div class="main-content">
            
            <div class="info-card">
                <p style="margin: 0;">Welcome, Admin. Here is a snapshot of system activity.</p>
            </div>
            
            <div class="stats-grid">
            
                <div class="stat-card stat-registrations">
                    <div class="stat-icon">&#128100;</div>
                    <div class="stat-content">
                        <div class="stat-value"><%= reg %></div>
                        <div class="stat-label">New Registrations</div>
                    </div>
                </div>

                <div class="stat-card stat-new-complaints">
                    <div class="stat-icon">&#9993;</div>
                    <div class="stat-content">
                        <div class="stat-value"><%= newComp %></div>
                        <div class="stat-label">New Complaints Today</div>
                    </div>
                </div>

                <div class="stat-card stat-total">
                    <div class="stat-icon">&#128220;</div>
                    <div class="stat-content">
                        <div class="stat-value"><%= total %></div>
                        <div class="stat-label">Total Active Complaints</div>
                    </div>
                </div>

                <div class="stat-card stat-pending">
                    <div class="stat-icon">&#9200;</div>
                    <div class="stat-content">
                        <div class="stat-value"><%= pend %></div>
                        <div class="stat-label">Pending (Needs Action)</div>
                    </div>
                </div>

                <div class="stat-card stat-in-progress">
                    <div class="stat-icon">&#x2699;</div>
                    <div class="stat-content">
                        <div class="stat-value"><%= progress %></div>
                        <div class="stat-label">In Progress</div>
                    </div>
                </div>

                <div class="stat-card stat-resolved">
                    <div class="stat-icon">&#x2705;</div>
                    <div class="stat-content">
                        <div class="stat-value"><%= resolved %></div>
                        <div class="stat-label">Resolved</div>
                    </div>
                </div>
                
            </div>
            
        </div>
    </div>
</body>
</html>