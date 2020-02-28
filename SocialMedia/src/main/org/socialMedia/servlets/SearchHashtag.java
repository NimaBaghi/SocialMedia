package org.socialMedia.servlets;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.socialMedia.entities.Friend;
import org.socialMedia.entities.Hashtag;
import org.socialMedia.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@WebServlet("/searchhashtag")
public class SearchHashtag extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hashtag = req.getParameter("searchHashtag");
        if (hashtag == null || hashtag.trim().equals("")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("search.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");

                sessionFactoryObj = configuration.buildSessionFactory();
                sessionObj = sessionFactoryObj.openSession();

                sessionObj.beginTransaction();

                User user = (User) req.getSession().getAttribute("userDetails");
                String searchHashtagQuery = "select distinct h from Friend f, User u inner join u.post p inner join p.hashtags h where u.profilePrivacy = 1 or (((p.user = f.fromID and f.toID =:meID) or (p.user = f.toID and f.fromID =:meID)) and f.status = 2) or p.user =:meID";
                Query query1 = sessionObj.createQuery(searchHashtagQuery);

                query1.setParameter("meID", user);

                List<Hashtag> hashtags = query1.list();

                ArrayList<String> searchHashtags = new ArrayList<>();
                for (int i = 0; i < hashtags.size(); i++) {
                    if (hashtags.get(i).getText().contains(hashtag)) {
                        searchHashtags.add(hashtags.get(i).getText());
                        req.setAttribute("findhashtag", "taken");
                    }
                }

                LinkedHashSet<String> removeDuplicates = new LinkedHashSet<>(searchHashtags);
                ArrayList<String> hashtagsWithoutDuplicates = new ArrayList<>(removeDuplicates);

                req.setAttribute("hashtags", hashtagsWithoutDuplicates);
                
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
