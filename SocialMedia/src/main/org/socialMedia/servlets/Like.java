package org.socialMedia.servlets;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.socialMedia.entities.LikeDetails;
import org.socialMedia.entities.Post;
import org.socialMedia.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/like")
public class Like extends HttpServlet {
    private static int i = 1;
    public static Session sessionObj;
    public static SessionFactory sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("userDetails");
        System.out.println(req.getParameter("postID"));
        String stringPostID = req.getParameter("postID");
        int postID = Integer.parseInt(stringPostID);
        LikeDetails like = new LikeDetails();
        like.setUserLiked(user);

        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();
            Query query1 = sessionObj.createQuery("from Post ");
            List<Post> posts = query1.list();

            Query query2 = sessionObj.createQuery("from LikeDetails where post=:pD");
            for (int i = 0; i < posts.size(); i++) {
                if (postID == posts.get(i).getPostID()) {
                    query2.setParameter("pD", posts.get(i));
                }
            }
            List<LikeDetails> likeDetails = query2.list();
            i = likeDetails.size() + 1;
            for (int i = 0; i < posts.size(); i++) {
                if (postID == posts.get(i).getPostID()) {
                    like.setPost(posts.get(i));
                }
            }
            resp.getWriter().println("Liked (" + i + ")");
            sessionObj.save(like);

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
    }
}
