<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<form action="register" method="post">
    <center>

        <% if (request.getAttribute("null") == "notok") {%>

        Username: <input type="text" name="username" value="<%=request.getAttribute("uname")%>">
        <br>
        <br>
        Full Name: <input type="text" name="fullname" value="<%=request.getAttribute("fname")%>">
        <br>
        <br>
        Password: <input type="password" name="password" value="<%=request.getAttribute("pass")%>">
        <br>
        <br>
        Profile Privacy:<br>

<%-- Something wrong with this if!!--%>
        <% if (request.getAttribute("privacy") == "0" ) {%>
        <input type="radio" name="privacy" value="1"> Public <br>
        <input type="radio" name="privacy" value="0" checked> Private <br>
        <%} else { %>
        <input type="radio" name="privacy" value="1" checked> Public <br>
        <input type="radio" name="privacy" value="0"> Private <br>
        <% } %>
        <br>
        <br>
        <input type="submit" value="Sign up"><br>
        <br>

        Have an account? <a href="login.jsp">Log in</a>

        <h1 style="color: red">
            Please insert correctly!
        </h1>
        <%} else { %>
        Username: <input type="text" name="username">
        <br>
        <br>
        Full Name: <input type="text" name="fullname">
        <br>
        <br>
        Password: <input type="password" name="password">
        <br>
        <br>
        Profile Privacy:<br>
        <input type="radio" name="privacy" value="1" checked> Public <br>
        <input type="radio" name="privacy" value="0"> Private <br>
        <br>
        <br>
        <input type="submit" value="Sign up"><br>
        <br>

        Have an account? <a href="login.jsp">Log in</a>

        <% } %>

        <% if (request.getAttribute("userTaken") == "notok") {%>
        <h1 style="color: red">
            Username taken please insert something else.
        </h1>
        <%}%>
    </center>
</form>
</body>
</html>
