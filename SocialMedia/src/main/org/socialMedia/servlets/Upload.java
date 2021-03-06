package org.socialMedia.servlets;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.socialMedia.entities.Hashtag;
import org.socialMedia.entities.Post;
import org.socialMedia.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.UUID;

@WebServlet("/upload")
@MultipartConfig
public class Upload extends HttpServlet {
    public static Session sessionObj;
    public static SessionFactory sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("userDetails");

        boolean fileSize = req.getPart("file").getSize() < 10000;
        // if the size of file is lower than 10kb so don't save it.

        if (fileSize) {
            req.setAttribute("uploadPost", "wrong");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("postDetails.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            Part filePart = req.getPart("file");

            String randomValue = UUID.randomUUID().toString();
            String fileName = randomValue + ".jpg";

            String caption = req.getParameter("caption");

            String inputHashtags = req.getParameter("hashtag");

            String[] hashtags = inputHashtags.split("#");


            String uploadLocation = req.getServletContext().getRealPath("/images");


            File imageLoc = new File(uploadLocation);

            if (!imageLoc.exists()) {
                imageLoc.mkdir();
            }

            String userLoc = uploadLocation + "\\" + user.getUserID();

            File upload = new File(userLoc);

            if (!upload.exists()) {
                upload.mkdir();
            }
            fileName = randomValue + ".jpg";
            File file = new File(upload, fileName);
            fileName = "images/" + user.getUserID() + "/" + fileName;

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            Post post = new Post(fileName, caption, new Date());
            post.setUser(user);

            try {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");

                sessionFactoryObj = configuration.buildSessionFactory();
                sessionObj = sessionFactoryObj.openSession();

                sessionObj.beginTransaction();

                for (int i = 1; i < hashtags.length; i++) {
                    String hashtagText = hashtags[i];
                    Hashtag hashtag = new Hashtag(hashtagText);
                    hashtag.getListOfPosts().add(post);
                    post.getHashtags().add(hashtag);
                    sessionObj.save(hashtag);
                }

                sessionObj.save(post);

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

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("postDetails.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
