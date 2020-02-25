<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="java.util.List" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="org.socialMedia.servlets.Upload" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.socialMedia.entities.*" %>
<%@ page import="java.util.HashSet" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
</head>
<body>
<center>
    <%! private Session sessionObj;
        private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;
        private ArrayList<Post> listOfPosts;
        private ArrayList<Post> userLiked;
        private List<Integer> postLikes;%>

    <button id="out">Sign out</button>
    <button id="addPost">Add a post</button>
    <button id="search">Search</button>
    <button id="rList">Requests List</button>
    <button id="fList">Friends List</button>
    <button id="myPro">My profile</button>

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
    <button id="notify">Notifactions(<%=counter%>)</button>
    <br>
    <br>

    <%
            Query query1 = sessionObj.createQuery("from Post");
            List<Post> posts = query1.list();

            Query query2 = sessionObj.createQuery("from Friend");
            List<Friend> friends = query2.list();

            listOfPosts = new ArrayList<Post>();
            for (int i = 0; i < posts.size(); i++) {
                for (int j = 0; j < friends.size(); j++) {
                    if (((posts.get(i).getUser().getUserID() == friends.get(j).getFromID().getUserID() &&
                            friends.get(j).getToID().getUserID() == me.getUserID()) && friends.get(j).getStatus() == 2) ||
                            ((posts.get(i).getUser().getUserID() == friends.get(j).getToID().getUserID() &&
                                    friends.get(j).getFromID().getUserID() == me.getUserID()) && friends.get(j).getStatus() == 2)) {
                        listOfPosts.add(posts.get(i));
                    }
                }
            }

            Query query3 = sessionObj.createQuery("from LikeDetails where userLiked=:me");
            query3.setParameter("me", me);
            List<LikeDetails> likes = query3.list();
            userLiked = new ArrayList<Post>();

            for (int i = 0; i < likes.size(); i++) {
                userLiked.add(likes.get(i).getPost());
            }


            Query query4 = sessionObj.createQuery("select count(post) from LikeDetails group by post");
            postLikes = query4.list();


            sessionObj.getTransaction().commit();
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
    <br>
    <br>

    <% if(listOfPosts.size() == 0 || listOfPosts == null){ %>
            Nothing Yet!
        <%} else {
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (int i = 0; i < userLiked.size(); i++) {
            hashSet.add(userLiked.get(i).getPostID());
        }
        for (int i = 0; i < listOfPosts.size(); i++) {%>
    <p><% out.print(listOfPosts.get(i).getUser().getUserName()); %> Posted:</p>
    <% String url = "images/" + listOfPosts.get(i).getUrl();%>
    <img src="<%= url %>" width="800" height="800"/>
    <br>

    <% if (hashSet.contains(listOfPosts.get(i).getPostID())) {%>

    <button type="button" id="bt#<%=listOfPosts.get(i).getPostID()%>"
            onclick='loadXMLDoc(<%=listOfPosts.get(i).getPostID()%>)' disabled>
        <div id="myDiv">Liked (<%
            for (int j = 1; j <= postLikes.size(); j++) {
                if (listOfPosts.get(i).getPostID() == j) {
                    out.print(postLikes.get(j - 1));
                }
            }
        %>)
        </div>
    </button>

    <%} else {%>

    <button type="button" id="bt#<%=listOfPosts.get(i).getPostID()%>"
            onclick='loadXMLDoc(<%=listOfPosts.get(i).getPostID()%>)'>Like
        <div id="myDiv"></div>
    </button>

    <% } %>
    <br>
    <p>Caption: <% out.print(listOfPosts.get(i).getCaption()); %></p>

    <%  try {
            sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("select h from Post p inner join p.hashtags h where p.postID=:pID");
            query.setParameter("pID", listOfPosts.get(i).getPostID());
            List<Hashtag> hashtags = query.list(); %>
    <p>Hashtags: <%
        for (int j = 0; j < hashtags.size(); j++) {
            out.print("#" + hashtags.get(j).getText());
        } %></p>
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
    <br>
    <br>
    <br>
    <% }
    }%>

</center>


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
    document.getElementById("myPro").onclick = function () {
        location.href = "myProfile.jsp";
    };document.getElementById("out").onclick = function () {
        location.href = "out";
    };
</script>

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
                document.getElementById("bt#" + e).disabled = true;
            }
        }
        xmlhttp.open("Post", url, true);
        xmlhttp.send();
    }
</script>
</body>
</html>