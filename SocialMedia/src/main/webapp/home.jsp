<%@ page import="org.socialMedia.entities.User" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="org.socialMedia.entities.Notification" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.socialMedia.servlets.Upload" %>
<%@ page import="org.socialMedia.entities.Post" %>
<%@ page import="org.socialMedia.entities.LikeDetails" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
</head>
<body>
<center>
    <%! private Session sessionObj;
        private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;
    %>
    <% User me = (User) request.getSession().getAttribute("userDetails");
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("from Notification");
            List<Notification> notificationList = query.list();
            int counter = 0;
            for (int i = 0; i < notificationList.size(); i++) {
                if (notificationList.get(i).getReaded() == 0 && notificationList.get(i).getUserNotification().getUserID() == me.getUserID()) {
                    counter++;
                }
            }
    %>
    <button id="addPost">Add a post</button>
    <button id="search">Search</button>
    <button id="rList">Requests List</button>
    <button id="fList">Friends List</button>
    <button id="notify">Notifactions(<%=counter%>)</button>
    <br>
    <br>
    <%
        Query query1 = sessionObj.createQuery("from Post ");
        List<Post> posts = query1.list();

//        Query query2 = sessionObj.createQuery("from LikeDetails where userLiked=:us");
//        query2.setParameter("us", me);
//        List<LikeDetails> like = query2.list();

        for (int i = 0; i < posts.size(); i++) {
    %>
    <button type="button" id="bt#<%=posts.get(i).getPostID()%>" onclick='loadXMLDoc(<%=posts.get(i).getPostID()%>)'>Like
        <div id="myDiv"></div>
    </button>
    <%
        }
    %>
    <script type="text/javascript">
        document.getElementById("addPost").onclick = function () {
            location.href = "upload.jsp";
        };
        document.getElementById("search").onclick = function () {
            location.href = "search.jsp";
        };
        document.getElementById("rList").onclick = function () {
            location.href = "requestList";
        };
        document.getElementById("fList").onclick = function () {
            location.href = "friendsList";
        };
        document.getElementById("notify").onclick = function () {
            location.href = "notif";
        };

    </script>
</center>

<br>
<center>
    <script type="text/javascript">
        function loadXMLDoc(e) {
            var xmlhttp;
            var url = "like?postID=" + e;
            if (window.XMLHttpRequest) {
                xmlhttp = new XMLHttpRequest();
            } else {
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    document.getElementById("bt#" + e).innerHTML = xmlhttp.responseText;
                }
            }
            xmlhttp.open("Post", url, true);
            xmlhttp.send();
        }

    </script>
    <% sessionObj.getTransaction().commit();
    } catch (Exception sqlException) {
        if (null != sessionObj.getTransaction()) {
            System.out.println("\n.......Transaction Is Being Rolled Back.......");
            sessionObj.getTransaction().rollback();
        }
        sqlException.printStackTrace();
    } finally {
        if (sessionObj != null) {
            sessionObj.close();
        }
    }
    %>
</center>
</body>
</html>