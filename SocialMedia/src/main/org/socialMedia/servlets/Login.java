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
import java.util.List;

@WebServlet("/login")
public class Login extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("from User");
            List<User> users = query.list();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserName().equals(userName)) {
                    if (users.get(i).getPassword().equals(password)) {
                        req.getSession().setAttribute("userDetails", users.get(i));
                        RequestDispatcher requestDispatcher = req.getRequestDispatcher("home.jsp");
                        requestDispatcher.forward(req, resp);
                    } else {
                        req.setAttribute("password", "wrong");
                        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
                        requestDispatcher.forward(req, resp);
                    }
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
        req.setAttribute("username", "wrong");
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(req, resp);
    }
}
