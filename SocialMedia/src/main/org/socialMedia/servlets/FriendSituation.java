package org.socialMedia.servlets;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.sql.Update;
import org.socialMedia.entities.Friend;
import org.socialMedia.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/friendsituation")
public class FriendSituation extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User fromUser = (User) req.getSession().getAttribute("userDetails");
        String[] accUsername = null;
        String[] rejUsername = null;
        if (req.getParameter("accept") != null) {
            String acceptUsername = req.getParameter("accept");
            accUsername = acceptUsername.split("#");
        } else {
            String rejectUsername = req.getParameter("reject");
            rejUsername = rejectUsername.split("#");
        }
        Friend friend = new Friend();
        friend.setToID(fromUser);

        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("from User ");
            List<User> users = query.list();

            if (req.getParameter("accept") != null) {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserName().equals(accUsername[1])) {
                        friend.setFromID(users.get(i));
                    }
                }

                Query query1 = sessionObj.createQuery("Update Friend Set status = 2 Where fromID =:fID AND toID =:tID ");
                query1.setParameter("fID", friend.getFromID());
                query1.setParameter("tID", friend.getToID());

                query1.executeUpdate();
            }
            else {
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserName().equals(rejUsername[1])) {
                        friend.setFromID(users.get(i));
                    }
                }

                Query query1 = sessionObj.createQuery("Update Friend Set status = 3 Where fromID =:fID AND toID =:tID ");
                query1.setParameter("fID", friend.getFromID());
                query1.setParameter("tID", friend.getToID());

                query1.executeUpdate();
            }

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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("home.jsp");
        requestDispatcher.forward(req, resp);
    }
}
