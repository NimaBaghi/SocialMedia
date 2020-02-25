package org.socialMedia.servlets;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.socialMedia.entities.Comment;
import org.socialMedia.entities.LikeDetails;
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

@WebServlet("/comm")
public class Comments extends HttpServlet {
    public static Session sessionObj;
    public static SessionFactory sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int postID = 0;
        User me = null;
        Comment comment = null;
        if (req.getParameter("com") == null) {
            String comment1 = req.getParameter("comment");
            String[] splitComment = comment1.split(" ");
            String stringPostID = splitComment[1];
            postID = Integer.parseInt(stringPostID);
            req.getSession().setAttribute("commentPostID", postID);
        } else {
            me = (User) req.getSession().getAttribute("userDetails");
            String context = req.getParameter("com");
            comment = new Comment(context);
            comment.setUserComment(me);
            postID = (int) req.getSession().getAttribute("commentPostID");
        }
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();
            if (req.getParameter("com") == null) {

                Query query1 = sessionObj.createQuery("from Post");
                List<Post> posts = query1.list();

                Query query2 = sessionObj.createQuery("from Comment where postComment=:pC");
                for (int i = 0; i < posts.size(); i++) {
                    if (postID == posts.get(i).getPostID()) {
                        query2.setParameter("pC", posts.get(i));
                    }
                }
                List<Comment> comments = query2.list();
                req.setAttribute("commentList", comments);


                Query query = sessionObj.createQuery("from Comment ");
                List<Comment> commentt = query.list();
                ArrayList<String> contexts = new ArrayList<String>();
                for (int i = 0; i < commentt.size(); i++) {
                    contexts.add(commentt.get(i).getContext());
                }
                req.setAttribute("contexts", contexts);
            } else {
                Query query1 = sessionObj.createQuery("from Post");
                List<Post> posts = query1.list();

                Query query2 = sessionObj.createQuery("from Comment where postComment=:pC");
                for (int i = 0; i < posts.size(); i++) {
                    if (postID == posts.get(i).getPostID()) {
                        query2.setParameter("pC", posts.get(i));
                    }
                }
                List<Comment> comments = query2.list();
                for (int i = 0; i < posts.size(); i++) {
                    if (postID == posts.get(i).getPostID()) {
                        comment.setPostComment(posts.get(i));
                    }
                }
                sessionObj.save(comment);
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
        if (req.getParameter("com") == null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("comments.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("home.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
