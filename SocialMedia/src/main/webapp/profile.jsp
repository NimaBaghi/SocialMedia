<%@ page import="org.socialMedia.entities.User" %>
<%@ page import="org.socialMedia.entities.Post" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<% User user = (User) request.getSession().getAttribute("userProfile"); %>
<head>
    <title><%= user.getUserName() %>
    </title>
</head>
<body>
<center>
    <button id="goHome">Home</button>
    <button id="search">Search</button>

    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
        document.getElementById("search").onclick = function () {
            location.href = "search.jsp";
        };
    </script>
    <br>
    <br>

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

    <% List<Post> userPo = (List<Post>) request.getSession().getAttribute("usersPosts"); %>

    <% if (userPo == null || userPo.size() == 0) { %>
        Nothing yet!
    <% } else {%>

    <% user.getUserName(); %> Posted:
    <br>

    <% for (int i = 0; i < userPo.size(); i++) {
        String url = "images/" + userPo.get(i).getUrl();
    %>

    <br>
    <br>

    <img src="<%= url %>" height="800" width="800">

    <% }
    } %>
</center>
</body>
</html>
