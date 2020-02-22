<%@ page import="java.util.ArrayList" %>
<%@ page import="org.socialMedia.servlets.Notifications" %>
<%@ page import="org.socialMedia.entities.Friend" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Notification</title>
</head>
<body>
<center>
    <%
        ArrayList<Friend> notificationsList = (ArrayList<Friend>) request.getAttribute("userNotifications");
        if (notificationsList.size() != 0) {
    %>
    Notifications:
    <br>
    <%
        for (int i = 0; i < notificationsList.size(); i++) {
    %><br><%
    out.println(notificationsList.get(i) + "Has requested you");
%><br><%
        }
    } else {
        out.print("You don't have requests, sorry!.");
    }%>
</center>
</body>
</html>
