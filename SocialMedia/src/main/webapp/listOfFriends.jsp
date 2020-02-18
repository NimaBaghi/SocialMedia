<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Friends List</title>
</head>
<body>
<center>
    <button id="goHome">Home</button>
    <button id="rList">Requests List</button>

    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
        document.getElementById("rList").onclick = function () {
            location.href = "requestList";
        };
    </script>

    <br>
    <br>

    <%
        ArrayList<String> username = (ArrayList<String>) request.getAttribute("userFriends");
        if (username.size() != 0) {
    %>
    Friends:
    <br>
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
