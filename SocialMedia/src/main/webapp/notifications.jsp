<%@ page import="java.util.ArrayList" %>
<%@ page import="org.socialMedia.servlets.Notifications" %>
<%@ page import="org.socialMedia.entities.Friend" %>
<%@ page import="org.socialMedia.entities.Notification" %>
<%@ page import="org.socialMedia.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Notification</title>
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
        User me = (User) request.getSession().getAttribute("userDetails");
        ArrayList<Notification> hasNotifications = (ArrayList<Notification>) request.getAttribute("userNotifications");
        if (hasNotifications.size() != 0) {
    %>
    Notifications:
    <br>
    <%
        for (int i = hasNotifications.size() - 1; i >= 0; i--) {
    %><br><%
    if (hasNotifications.get(i).getFromID() != null && hasNotifications.get(i).getReaded() == 0) {
        out.println(hasNotifications.get(i).getFromID().getUserName() + " has requested you!");
%><br><%
    }
    if (hasNotifications.get(i).getLikeNotification() != null) {
        out.println(hasNotifications.get(i).getLikeNotification().getUserLiked().getUserName() + " liked your post!");
    }
    if (hasNotifications.get(i).getCommentNotification() != null && hasNotifications.get(i).getReaded() == 0) {
        out.println(hasNotifications.get(i).getCommentNotification().getUserComment().getUserName() + " send comment on your post!");
%><br><%
            }
        }
    } else {
        out.print("You don't have notifications, sorry!.");
    }%>
</center>
</body>
</html>
