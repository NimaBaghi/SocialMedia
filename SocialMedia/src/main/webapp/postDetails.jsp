<%@ page import="org.socialMedia.entities.Post" %>
<%@ page import="org.socialMedia.servlets.Upload" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="java.io.Serializable" %><%--
  Created by IntelliJ IDEA.
  User: RaXeL
  Date: 2/4/2020
  Time: 1:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--       Post post=(Post)request.getSession().getAttribute("postSubmit");--%>

<%--    int postID = (Integer) request.getSession().getAttribute("postID");--%>
<%--   Session sessionDB = Upload.sessionFactoryObj.openSession();--%>
<%--   sessionDB.beginTransaction();--%>
<%--    post = (Post) sessionDB.get(Post.class, post.getPostID());--%>
<%--    out.print(post.getPostID());--%>
<%--    Upload.postID++;--%>

<%--    String url = post.getUrl();--%>
<%--    url = url.trim();%>--%>


<center>


    Post successfuly added.
    <%--    <img src=images/<%=url%>/>--%>
    <%--    <br>--%>
    <%--    <br>--%>
    <%--    <img src="<%="images/"+url%>"/>--%>
    <%--    <br>--%>
    <%--    <br>--%>
    <%--    <img src="images/<%=url%>"/>--%>
    <%--    <br>--%>
    <%--    <br>--%>
    <%--    <%out.println("images/"+url.trim());%>--%>
</center>
</body>
</html>
