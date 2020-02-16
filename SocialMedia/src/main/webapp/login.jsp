<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="login" method="post">
    <center>
        Username: <input type="text" name="username">
        <br>
        <br>
        Password: <input type="password" name="password">
        <br>
        <br>
        <input type="submit" value="Log In">
        <br>
        <br>
        Don't have an account?<a href="register.jsp"> Sign up</a>

        <br>
        <br>

        <% if (request.getSession(true).getAttribute("register") == "successful") {%>
        <h1 style="color: green">
            You have successfully registered.
        </h1>
        <% request.getSession().setAttribute("register", "done"); %>
        <%}%>

        <% if (request.getAttribute("password") == "wrong") {%>
        <h1 style="color: red">
            Wrong password. Try it again!
        </h1>
        <% request.setAttribute("password", "done"); %>
        <%}%>

        <% if (request.getAttribute("username") == "wrong") {%>
        <h1 style="color: red">
            Wrong username. Try it again!
        </h1>
        <% request.setAttribute("username", "done"); %>
        <%}%>

    </center>
</form>
</body>
</html>
