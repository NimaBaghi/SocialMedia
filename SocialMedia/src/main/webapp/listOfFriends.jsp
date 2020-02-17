<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Friends List</title>
</head>
<body>
<center>
    <%ArrayList<String> username = (ArrayList<String>) request.getAttribute("userFriends");
        if (username.size() != 0) {
            %>
    <%
        for (int i = 0; i < username.size(); i++) {
    %><br><%
    out.println(username.get(i));
    %><br><%
        }
    } else {
        out.print("You don't have friends, sorry!.");
    }%>
</center>
</body>
</html>
