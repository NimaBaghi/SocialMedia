package org.socialMedia.servlets;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.socialMedia.entities.Friend;
import org.socialMedia.entities.Post;
import org.socialMedia.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/profile")
public class Profile extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userFound = req.getParameter("userFound");
        User toUser = null;
        User fromUser = (User) req.getSession().getAttribute("userDetails");
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("from User");
            List<User> users = query.list();

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName().equals(userFound)) {
                    req.getSession().setAttribute("userProfile", users.get(i));
                    toUser = users.get(i);
                }
            }
            query = sessionObj.createQuery("from Friend ");
            List<Friend> friends = query.list();

            for (int i = 0; i < friends.size(); i++) {
                if (toUser.getUserID() == friends.get(i).getToID().getUserID() && fromUser.getUserID() == friends.get(i).getFromID().getUserID()) {
                    req.setAttribute("added", "successful");
                }

                if (friends.get(i).getFromID().getUserID() == toUser.getUserID() && friends.get(i).getToID().getUserID() == fromUser.getUserID() && friends.get(i).getStatus() == 1) {
                    req.setAttribute("added", "requested");
                }

                if (toUser.getUserID() == friends.get(i).getToID().getUserID() && fromUser.getUserID() == friends.get(i).getFromID().getUserID() && friends.get(i).getStatus() == 3) {
                    req.setAttribute("added", "requestRejected");
                }

                if (friends.get(i).getFromID().getUserID() == fromUser.getUserID() && friends.get(i).getToID().getUserID() == toUser.getUserID() && friends.get(i).getStatus() == 2) {
                    req.setAttribute("added", "friends");
                } else if (friends.get(i).getFromID().getUserID() == toUser.getUserID() && friends.get(i).getToID().getUserID() == fromUser.getUserID() && friends.get(i).getStatus() == 2) {
                    req.setAttribute("added", "friends");
                }
            }

            if (fromUser.getUserID() == toUser.getUserID()) {
                req.setAttribute("added", "self");
            }

            Query query1 = sessionObj.createQuery("select p from User u inner join u.post p where p.user =:userpro");
            query1.setParameter("userpro", toUser);
            List<Post> userPosts = query1.list();

            req.getSession().setAttribute("usersPosts", userPosts);

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

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("profile.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
