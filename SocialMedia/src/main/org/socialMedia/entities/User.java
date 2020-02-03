package org.socialMedia.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private int userID;

    @Column(name = "USERNAME")
    private String userName;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PROFILE_PICTURE")
    private String profilePicture;

    @Column(name = "PROFILE_PRIVACY")
    private int profilePrivacy;

    @OneToMany(mappedBy = "user")
    private Collection<Post> post = new ArrayList<Post>();

    @OneToMany(mappedBy = "userLiked")
    private Collection<LikeDetails> liked = new ArrayList<LikeDetails>();

    @OneToMany(mappedBy = "userComment")
    private Collection<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "userNotification")
    private Collection<Notification> notifications = new ArrayList<Notification>();

    public User() {
    }

    public User(String userName, String fullName, String password, int profilePrivacy) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.profilePrivacy = profilePrivacy;
    }

    public User(String userName, String fullName, String password, String profilePicture, int profilePrivacy) {
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
        this.profilePicture = profilePicture;
        this.profilePrivacy = profilePrivacy;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getProfilePrivacy() {
        return profilePrivacy;
    }

    public void setProfilePrivacy(int profilePrivacy) {
        this.profilePrivacy = profilePrivacy;
    }

    public Collection<Post> getPost() {
        return post;
    }

    public void setPost(Collection<Post> post) {
        this.post = post;
    }

    public Collection<LikeDetails> getLiked() {
        return liked;
    }

    public void setLiked(Collection<LikeDetails> liked) {
        this.liked = liked;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
    }
}
