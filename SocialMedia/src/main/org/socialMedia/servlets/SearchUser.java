package org.socialMedia.servlets;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
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

@WebServlet("/searchuser")
public class SearchUser extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("searchUser");
        if (userName == null || userName.trim().equals("")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("search.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");

                sessionFactoryObj = configuration.buildSessionFactory();
                sessionObj = sessionFactoryObj.openSession();

                sessionObj.beginTransaction();

                Query query = sessionObj.createQuery("select userName from User");
                List<String> userNames = query.list();
                ArrayList<String> searchUser = new ArrayList<>();
                for (int i = 0; i < userNames.size(); i++) {
                    if (userNames.get(i).contains(userName)) {
                        searchUser.add(userNames.get(i));
                        req.setAttribute("findUser", "taken");
                        req.setAttribute("userNames", searchUser);
                    }
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
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("search.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}