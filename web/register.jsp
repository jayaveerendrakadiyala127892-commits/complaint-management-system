<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Complaint System</title>
    <link rel="stylesheet" type="text/css" href="css/style_register.css">
</head>
<body>
    <div class="register-container">
        
        <div class="register-form-panel">
            <h2>Register</h2>
            <p class="tagline">Create your user account</p>
            
            <form method="post" action="register">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                
                <button type="submit">Register Account</button>
            </form>

            <div class="footer-links">
                <p>Already have an account?</p>
                <a href="login.jsp" class="login-link">Login</a>
            </div>

            <% if (request.getParameter("error") != null) { %>
                <p class="error">Registration failed. Try a different email.</p>
            <% } %>
        </div>
        
        <div class="welcome-panel">
            <h1>Welcome to <span class="unbolded">Complaint portal</span></h1>
            <p>Create your account to submit and track your issues.</p>
            </div>
        
    </div>
</body>
</html>