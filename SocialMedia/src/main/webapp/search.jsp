<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<body>
<center>
    <button id="goHome">Home</button>

    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
    </script>
    <br>
    <br>



    <form action="searchuser" method="post">
        <input type="text" name="searchUser">
        <input type="submit" value="Search User">
        <br>
    </form>


    <form action="searchhashtag" method="post">
        <input type="text" name="searchHashtag">
        <input type="submit" value="Search Hashtag">
        <br>
    </form>

    <form method="POST" action="profile">
        <%
            if (request.getAttribute("findUser") != null) {
        %>
        Users found:
        <br>
        <%
                ArrayList<String> userName = (ArrayList<String>) request.getAttribute("userNames");
                for (int i = 0; i < userName.size(); i++) {

        %>

        <input type="submit" value="<%=userName.get(i)%>" name="userFound">
        <br>

        <%
                }
            }
        %>
    </form>

    <form method="POST" action="hashtagPrepare">

        <%
            if (request.getAttribute("findhashtag") != null) {
                %>
        Hashtags found:
        <br>
        <%
                ArrayList<String> hashtags = (ArrayList<String>) request.getAttribute("hashtags");
                for (int i = 0; i < hashtags.size(); i++) {

        %>

        <input type="submit" value="<%=hashtags.get(i)%>" name="hashtagFound">
        <br>

        <%
                }
            }
        %>
    </form>


</center>
</body>
</html>