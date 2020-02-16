<%@ page import="org.socialMedia.entities.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<% User user = (User) request.getSession().getAttribute("userProfile"); %>
<head>

    <title><%= user.getUserName() %>
    </title>
</head>
<body>
<center>
    <form action="invitation" method="post">

        <%= user.getFullName()%>

        <input type="submit" value="Add Friend">
    </form>

</center>
</body>
</html>
