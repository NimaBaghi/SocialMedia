<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<body>
<center>
    <button id="goHome">Home</button>

    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
    </script>
    <br>
    <br>

    <form action="search" method="post">
        <input type="text" name="search">
        <input type="submit" value="Search">
        <br>
    </form>

    <form method="POST" action="profile">
        <%
            if (request.getAttribute("findUser") != null) {
                ArrayList<String> userName = (ArrayList<String>) request.getAttribute("userNames");
                for (int i = 0; i < userName.size(); i++) {

        %>

        <input type="submit" value="<%=userName.get(i)%>" name="userFound">
        <br>

        <%
                }
            }
        %>

    </form>
</center>
</body>
</html>