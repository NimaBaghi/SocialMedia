package org.socialMedia.servlets;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.socialMedia.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/register")
public class Register extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String fullName = req.getParameter("fullname");
        String password = req.getParameter("password");
        String pri = req.getParameter("privacy");
        int privacy = Integer.parseInt(pri); //private = 0, public = 1

        if (userName == null || userName.trim().equals("") || fullName == null || fullName.trim().equals("") || password == null || password.trim().equals("")) {
            req.setAttribute("fields", "null");
            req.setAttribute("uname", userName);
            req.setAttribute("fname", fullName);
            req.setAttribute("pass", password);
            req.setAttribute("privacy", pri);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("register.jsp");
            requestDispatcher.forward(req, resp);
        } else {

            User user = new User(userName, fullName, password, privacy);


            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");

                sessionFactoryObj = configuration.buildSessionFactory();
                sessionObj = sessionFactoryObj.openSession();

                sessionObj.beginTransaction();

                Query query = sessionObj.createQuery("select userName from User");
                List<String> userNames = query.list();
                for (int i = 0; i < userNames.size(); i++) {
                    if (userNames.get(i).equals(userName)) {
                        req.setAttribute("username", "taken");
                        RequestDispatcher requestDispatcher = req.getRequestDispatcher("register.jsp");
                        requestDispatcher.forward(req, resp);
                        sessionObj.getTransaction().commit();
                        return;
                    }
                }

                sessionObj.save(user);

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
            req.getSession().setAttribute("register", "successful");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
