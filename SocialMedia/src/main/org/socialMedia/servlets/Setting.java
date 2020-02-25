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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@WebServlet("/setting")
@MultipartConfig
public class Setting extends HttpServlet {
    private Session sessionObj;
    private SessionFactory sessionFactoryObj = Upload.sessionFactoryObj;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("userDetails");

        String uploadLocation = "";
        String fileName = "profilepicture.jpg";
        if (req.getPart("file") != null) {
            Part filePart = req.getPart("file");
            System.out.println("file: " + filePart);

            uploadLocation = "E:\\SocialMedia\\SocialMedia\\src\\main\\webapp\\images";
            uploadLocation = uploadLocation + "\\" + user.getUserID();

            File uploads = new File(uploadLocation);

            if (!uploads.exists()) {
                uploads.mkdir();
            }
            File file = new File(uploads, fileName);

            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }

        String pri = req.getParameter("privacy");
        int privacy = Integer.parseInt(pri); //private = 0, public = 1
        user.setProfilePrivacy(privacy);

        String loc = "/images/" + user.getUserID() + "/" + fileName;
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

            sessionFactoryObj = configuration.buildSessionFactory();
            sessionObj = sessionFactoryObj.openSession();

            sessionObj.beginTransaction();

            String queryString = "";
            if (req.getPart("file") == null) {
                queryString = "Update User Set profilePrivacy =:profileprivacy Where userID =:uID AND userName =:username";
            } else {
                queryString = "Update User Set profilePrivacy =:privacy, profilePicture =:pictureurl Where userID =:uID AND userName =:username";
            }
            Query query = sessionObj.createQuery(queryString);
            query.setParameter("uID", user.getUserID());
            query.setParameter("username", user.getUserName());
            query.setParameter("privacy", privacy);
            if (req.getPart("file") != null) {
                query.setParameter("pictureurl", loc);
                user.setProfilePicture(loc);
            }
            query.executeUpdate();

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

        req.getSession().setAttribute("userDetails", user);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("myProfile.jsp");
        requestDispatcher.forward(req, resp);
    }
}
