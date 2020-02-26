package org.socialMedia.servlets;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.socialMedia.entities.Friend;
import org.socialMedia.entities.Hashtag;
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

@WebServlet("/hashtagPrepare")
public class HashtagPrepare extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hashtagFound = req.getParameter("hashtagFound");
        User me = (User) req.getSession().getAttribute("userDetails");
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();

            Query query = sessionObj.createQuery("from Hashtag");
            List<Hashtag> hashtags = query.list();

            Hashtag clicked = null;
            for (int i = 0; i < hashtags.size(); i++) {
                if (hashtags.get(i).getText().equals(hashtagFound)) {
                    req.setAttribute("hashtagClicked", hashtags.get(i));
                    clicked = hashtags.get(i);
                }
            }
            Query query1 = sessionObj.createQuery("select p from Post p inner join p.hashtags h where h.text =:hashtext");
            query1.setParameter("hashtext", clicked.getText());
            List<Post> allPostsForHashtag = query1.list();



            Query query2 = sessionObj.createQuery("from Friend ");
            List<Friend> friends = query2.list();
            ArrayList<User> userFriends = new ArrayList<>();

            for (int i = 0; i < friends.size(); i++) {
                if (me.getUserID() == friends.get(i).getToID().getUserID() && friends.get(i).getStatus() == 2) {
                    userFriends.add(friends.get(i).getFromID());
                } else if (me.getUserID() == friends.get(i).getFromID().getUserID() && friends.get(i).getStatus() == 2) {
                    userFriends.add(friends.get(i).getToID());
                }
            }

            List<Post> postsForHashtag = new ArrayList<>();
            for (int i = 0; i < allPostsForHashtag.size(); i++) {
                for (int j = 0; j < userFriends.size(); j++) {
                    if(allPostsForHashtag.get(i).getUser().getUserID() == userFriends.get(j).getUserID() || allPostsForHashtag.get(i).getUser().getUserID() == me.getUserID()){
                        postsForHashtag.add(allPostsForHashtag.get(i));
                        break;
                    }
                }
            }

            req.setAttribute("hashtagPosts", postsForHashtag);

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

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("hashtagPosts.jsp");
        requestDispatcher.forward(req, resp);
    }
}
