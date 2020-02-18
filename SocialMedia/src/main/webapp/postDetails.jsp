<%@ page import="org.socialMedia.entities.Post" %>
<%@ page import="org.socialMedia.servlets.Upload" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="java.io.Serializable" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Posted</title>
</head>
<body>
<center>
    <button id="goHome">Back to home</button>
    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
    </script>

    <br>
    <br>

    Post successfuly added.
</center>
</body>
</html>
