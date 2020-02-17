package org.socialMedia.servlets;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
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

@WebServlet("/accept")
public class Accept extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User fromUser = (User) req.getSession().getAttribute("userDetails");
        String acceptUsername = req.getParameter("accept");
        String[] username = acceptUsername.split("#");
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

            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName().equals(username[1])) {
                    friend.setFromID(users.get(i));
                }
            }
            query = sessionObj.createQuery("from Friend ");
            List<Friend> friends = query.list();
            for (int i = 0; i < friends.size(); i++) {
                if (friends.get(i).getFromID().getUserID() == friend.getFromID().getUserID()) {
                    if (friends.get(i).getToID().getUserID() == friend.getToID().getUserID()) {
                        friend.setFriendID(friends.get(i).getFriendID());
                    }
                }
            }
            friend.setStatus(2);
            sessionObj.update(friend);

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
