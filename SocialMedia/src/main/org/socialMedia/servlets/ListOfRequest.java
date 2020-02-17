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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/requestList")
public class ListOfRequest extends HttpServlet {
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

            Query query = sessionObj.createQuery("from Friend ");
            List<Friend> friends = query.list();
            ArrayList<String> userNameList = new ArrayList<>();

            for (int i = 0; i < friends.size(); i++) {
                if (me.getUserID() == friends.get(i).getToID().getUserID() && friends.get(i).getStatus() == 1) {
                    userNameList.add(friends.get(i).getFromID().getUserName());
                }
            }
            req.setAttribute("userList", userNameList);
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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("listOfRequest.jsp");
        requestDispatcher.forward(req, resp);
    }
}
