package org.socialMedia.servlets;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.socialMedia.entities.Friend;
import org.socialMedia.entities.Notification;
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

@WebServlet("/invitation")
public class Invitation extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User fromUser = (User) req.getSession().getAttribute("userDetails");
        User toUser = (User) req.getSession().getAttribute("userProfile");
        Notification notification = new Notification();
        Friend friend = new Friend(1);
        friend.setFromID(fromUser);
        friend.setToID(toUser);
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("from Friend");
            List<Friend> friends = query.list();
            boolean rejected = false;
            for (int i = 0; i < friends.size(); i++) {
                if (friends.get(i).getFromID().getUserID() == fromUser.getUserID() && friends.get(i).getToID().getUserID() == toUser.getUserID() && friends.get(i).getStatus() == 3) {
                    rejected = true;
                }
            }
            if (!rejected) {
                sessionObj.save(friend);
            } else if (rejected) {
                Query query1 = sessionObj.createQuery("Update Friend Set status = 1 Where fromID =:fID AND toID =:tID ");
                query1.setParameter("fID", friend.getFromID());
                query1.setParameter("tID", friend.getToID());

                query1.executeUpdate();
            }
            //NotificationReaded=0 ==> Unread
            //NotificationReaded=1 ==> Have read
            notification.setReaded(0);
            notification.setFromID(friend.getFromID());
            notification.setUserNotification(toUser);
            sessionObj.save(notification);
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
        req.setAttribute("added", "successful");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("profile.jsp");
        requestDispatcher.forward(req, resp);
    }
}
