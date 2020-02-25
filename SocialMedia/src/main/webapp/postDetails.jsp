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
    <button id="addPost">Add a post</button>
    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
        document.getElementById("addPost").onclick = function () {
            location.href = "upload.jsp";
        };
    </script>

    <br>
    Careful if you insert two picture with same name it will show one of them!
    <br>
    <% if (request.getAttribute("uploadPost") != null) { %>
    <h1 style="color: red">
    Please insert picture!
    </h1>
    <% } else { %>
    <h1 style="color: green">
    Post successfuly added.
    </h1>
    <% } %>
</center>
</body>
</html>
