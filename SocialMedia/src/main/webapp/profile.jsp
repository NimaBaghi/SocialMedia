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
        <%if (request.getAttribute("added") == null || request.getAttribute("added") == "requestRejected") {%>
        <input type="submit" value="Add Friend">
        <%
        } else if (request.getAttribute("added") == "successful") {
        %>
        <input type="submit" value="Request Sent" disabled>
        <%
        } else if (request.getAttribute("added") == "friends") {
        %>
        <input type="submit" value="Friends" disabled>
        <%
        } else if (request.getAttribute("added") == "requested") {
        %>
        <input type="submit" formaction="requestList" formmethod="get" value="<%=user.getUserName()%> Requeted You">
        <% } %>
    </form>

</center>
</body>
</html>
