<%@ page import="com.complaintmanagement.model.User" %>
<%@ page session="true" %>
<%
    if (session.getAttribute("user") != null) {
        response.sendRedirect("submitComplaint.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Complaint System</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="login-container">
        
        <div class="welcome-panel">
            <h1>Welcome to <span class="unbolded">Complaint portal</span></h1>
            <p>Login to access your account</p>
            </div>

        <div class="login-form-panel">
            <h2>Login</h2>
            <p class="tagline">Enter your account details</p>
            
            <form method="post" action="login">
                <label for="email">Username:</label>
                <input type="email" id="email" name="email" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                
                <p class="forgot-link"><a href="#">Forgot Password?</a></p>

                <button type="submit">Login</button>
            </form>

            <div class="footer-links">
                <p>Don't have an account?</p>
                <a href="register.jsp">Sign up</a>
            </div>

            <% if (request.getParameter("error") != null) { %>
                <p class="error">Invalid email or password</p>
            <% } else if ("registered".equals(request.getParameter("msg"))) { %>
                <p class="success">Registration successful. Please login.</p>
            <% } %>
        </div>
        
    </div>
</body>
</html> 