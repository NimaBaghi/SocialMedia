<%@ page import="org.socialMedia.entities.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% User user = (User) request.getSession().getAttribute("userDetails"); %>
    <title><%= user.getUserName()%>
    </title>
</head>
<body>
<center>
    <button id="goHome">Home</button>
    <button id="set">Settings</button>

    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
        document.getElementById("set").onclick = function () {
            location.href = "settings.jsp";
        };
    </script>
    <br>
    <br>
    <% if (user.getPost() != null) { %>
    Posts that you Uploaded

<%--    <% for (int i = 0; i < user.getPost().size(); i++) {--%>
<%--        //fill with posts--%>
<%--    }--%>
    <%
    } else { %>
    Nothing posted yet!
    <% } %>
</center>
</body>
</html>
