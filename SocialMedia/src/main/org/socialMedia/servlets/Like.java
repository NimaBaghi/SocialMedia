package org.socialMedia.servlets;

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

@WebServlet("/like")
public class Like extends HttpServlet {
    private static int i = 0;
    public static Session sessionObj;
    public static SessionFactory sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        i++;
        resp.getWriter().println(i);
        User user = (User)req.getSession().getAttribute("userDetails");
        Post post = new Post("faraz1`123132","faraz",new Date());
        System.out.println("username: "+user.getUserName());
        System.out.println("User ID: "+user.getUserID());

        LikeDetails like = new LikeDetails();
        like.setUserLiked(user);
        like.setPost(post);



        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();

            sessionObj.save(post);
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
