<%@ page import="org.socialMedia.entities.User" %>
<%@ page import="org.hibernate.cfg.Configuration" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="org.socialMedia.entities.Post" %>
<%@ page import="java.util.List" %>
<%@ page import="org.socialMedia.entities.LikeDetails" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.hibernate.SessionFactory" %>
<%@ page import="java.util.ArrayList" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% User user = (User) request.getSession().getAttribute("userDetails");%>
    <title><%= user.getUserName()%>
    </title>
</head>
<body>
<center>
    <button id="goHome">Home</button>
    <button id="set">Settings</button>

    <script type="text/javascript">
        document.getElementById("goHome").onclick = function () {
            location.href = "home.jsp";
        };
        document.getElementById("set").onclick = function () {
            location.href = "settings.jsp";
        };
    </script>
    <br>
    <br>
    Profile picture:
    <br>
    <%
        String url = user.getProfilePicture();
    %>
    <img height="400px" width="400px" style="border: 3px solid salmon;" src="<%=url%>">
    <br>
    <br>
    <br>
    <%!
        public static Session sessionObj;
        public static SessionFactory sessionFactoryObj;
    %>
    Your posts:
    <br>
    <br>
    <%
        List<Post> posts = new ArrayList<Post>();
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();
            Query query = sessionObj.createQuery("from Post where user=:me");
            query.setParameter("me", user);
            posts = query.list();

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
        if (posts.size() != 0) {
            for (int i = 0; i < posts.size(); i++) {
                String myPostUrl = posts.get(i).getUrl();
    %>
    <img height="800px" width="800px" style="border: 3px solid salmon;" src="/images/<%=myPostUrl%>">
    <br>
    <%=posts.get(i).getCaption()%>
    <br>
    <br>
    <%
        }
    } else { %>
    Nothing posted yet!
    <% } %>
</center>
</body>
</html>