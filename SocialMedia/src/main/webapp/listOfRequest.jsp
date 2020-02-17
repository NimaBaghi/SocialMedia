<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ListOfRequest</title>
</head>
<body>
<form method="post" action="friendsituation">
    <center>
        <%
            if (request.getAttribute("userList") != null) {
                ArrayList<String> userName = (ArrayList<String>) request.getAttribute("userList");%>
        <%
            for (int i = 0; i < userName.size(); i++) {
        %><br>
        <input type="submit" value="Accept#<%=userName.get(i)%>" name="accept">
        <input type="submit" value="Reject#<%=userName.get(i)%>" name="reject">

        <%
            out.println(userName.get(i));
        %><br><%
            }
        } else {
            out.print("When people ask to follow you, you'll see their requests here.");
        }%>
    </center>
</form>
</body>
</html>
