<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<body>
<center>
    <form action="search" method="post">
        <input type="text" name="search">
        <input type="submit" value="Search">
        <br>
        <br>
        <%
            if (request.getAttribute("findUser") != null) {
                ArrayList<String> userName = (ArrayList<String>) request.getAttribute("userNames");
                for (int i = 0; i < userName.size(); i++) {
                    out.println(userName.get(i));%>
        <br>
        <%
                }
            }
        %>
    </form>
</center>
</body>
</html>