<%@ page import="org.socialMedia.entities.Hashtag" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% Hashtag hashtag = (Hashtag) request.getAttribute("hashtagClicked"); %>
    <title>#<%=hashtag.getText()%>
    </title>
</head>
<body>
<center>
    <button id="goHome">Home</button>
    <button id="search">Search</button>

    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
        document.getElementById("search").onclick = function () {
            location.href = "search.jsp";
        };
    </script>
    <br>
    <br>
</center>
</body>
</html>
