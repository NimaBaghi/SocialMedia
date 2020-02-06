package org.socialMedia.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private int postID;

    @Column(name = "URL")
    @Lob
    private String url;

    @Column(name = "CAPTION")
    @Lob
    private String caption;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;


    @ManyToMany(mappedBy = "listOfPosts")
    private Collection<Hashtag> hashtags = new ArrayList<Hashtag>();


    @OneToMany(mappedBy = "post")
    private Collection<LikeDetails> likes = new ArrayList<LikeDetails>();

    @OneToMany(mappedBy = "postComment")
    private Collection<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "postNotification")
    private Collection<Notification> notifications = new ArrayList<Notification>();

    public Post() {
    }

    public Post(String url, String caption, Date date) {
        this.url = url;
        this.caption = caption;
        this.date = date;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Collection<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Collection<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public Collection<LikeDetails> getLikes() {
        return likes;
    }

    public void setLikes(Collection<LikeDetails> likes) {
        this.likes = likes;
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
