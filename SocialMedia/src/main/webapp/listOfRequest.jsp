<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Requests List</title>
</head>
<body>
<center>
    <button id="goHome">Home</button>
    <button id="fList">Friends List</button>

    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
        document.getElementById("fList").onclick = function () {
            location.href = "friendsList";
        };
    </script>

    <br>
    <br>
</center>
<form method="post" action="friendsituation">
    <center>
        <%
            ArrayList<String> username = (ArrayList<String>) request.getAttribute("userList");
            if (username.size() != 0) {
        %>
        <%
            for (int i = 0; i < username.size(); i++) {
        %><br>
        <input type="submit" value="Accept#<%=username.get(i)%>" name="accept">
        <input type="submit" value="Reject#<%=username.get(i)%>" name="reject">

        <%
            out.println(username.get(i));
        %><br><%
            }
        } else {
            out.print("When people ask to follow you, you'll see their requests here.");
        }%>
    </center>
</form>
</body>
</html>
