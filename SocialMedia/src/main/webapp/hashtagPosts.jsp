<%@ page import="org.socialMedia.entities.Hashtag" %>
<%@ page import="org.socialMedia.entities.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.LinkedHashSet" %>

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

    <% ArrayList<Post> postsFound = (ArrayList<Post>) request.getAttribute("hashtagPosts"); %>

    <% for (int i = 0; i < postsFound.size(); i++) { %>

    <br>
    <br>
    <br>

    <% String url = postsFound.get(i).getUrl(); %>

    <%out.print(postsFound.get(i).getUser().getUserName()); %>  Posted:

    <br>
    <br>

    <img src="<%= url %>" height="800" width="800">
    <br>
    <br>

    Caption: <% out.print(postsFound.get(i).getCaption()); %>
    <% } %>

</center>
</body>
</html>
