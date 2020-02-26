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

@WebServlet("/notif")
public class Notifications extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User me = (User) req.getSession().getAttribute("userDetails");
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("from Notification");
            List<Notification> notificationList = query.list();
            ArrayList<Notification> hasNotifications = new ArrayList<>();

            for (int i = 0; i < notificationList.size(); i++) {
                if (notificationList.get(i).getUserNotification().getUserID() == me.getUserID()) {
                    if (notificationList.get(i).getFromID() != null) {
                        Query query1 = sessionObj.createQuery("Update Notification Set readed = 1 Where fromID =:fID AND userNotification =:tID ");
                        query1.setParameter("fID", notificationList.get(i).getFromID());
                        query1.setParameter("tID", notificationList.get(i).getUserNotification());
                        query1.executeUpdate();
                        hasNotifications.add(notificationList.get(i));
                    }
                    if (notificationList.get(i).getCommentNotification() != null) {
                        Query query2 = sessionObj.createQuery("Update Notification Set readed = 1 Where commentNotification=:cmndID and postNotification=:post and userNotification =:tID ");
                        query2.setParameter("cmndID", notificationList.get(i).getCommentNotification());
                        query2.setParameter("post", notificationList.get(i).getPostNotification());
                        query2.setParameter("tID", notificationList.get(i).getUserNotification());
                        query2.executeUpdate();
                        hasNotifications.add(notificationList.get(i));
                    }
                    if(notificationList.get(i).getLikeNotification() != null){
                        Query query3 = sessionObj.createQuery("Update Notification Set readed = 1 Where likeNotification=:likeID and postNotification=:post and userNotification =:tID ");
                        query3.setParameter("likeID", notificationList.get(i).getLikeNotification());
                        query3.setParameter("post", notificationList.get(i).getPostNotification());
                        query3.setParameter("tID", notificationList.get(i).getUserNotification());
                        query3.executeUpdate();
                        hasNotifications.add(notificationList.get(i));
                    }
                }
            }
            req.setAttribute("userNotifications", hasNotifications);
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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("notifications.jsp");
        requestDispatcher.forward(req, resp);
    }
}
