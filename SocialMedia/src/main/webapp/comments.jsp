<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.socialMedia.entities.Comment" %>
<%@ page import="org.socialMedia.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Comments</title>
</head>
<body>
<center>
    <button id="goHome">Home</button>
    <br>
    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
    </script>
    <%
        ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("commentList");
        for (int i = 0; i < comments.size(); i++) {
            User user = comments.get(i).getUserComment();
    %>
    <%=user.getUserName()%>: <%=comments.get(i).getContext()%>
    <br>
    <br>
    <% }%>
    <form method="post" action="comm">
        <input type="text" name="com">
        <input type="submit">
    </form>
</center>
</body>
</html>