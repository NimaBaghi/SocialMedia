package org.socialMedia.entities;

import javax.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue
    @Column(name = "NOTIFICATION_ID")
    private int notificationID;

    @Column(name = "READED")
    private int readed;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post postNotification;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userNotification;

    @OneToOne
    @JoinColumn(name = "LIKE_ID")
    private LikeDetails likeNotification;

    @OneToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment commentNotification;

    @OneToOne
    @JoinColumn(name = "FRIEND_ID")
    private Friend friendStatus;


    public Notification(int readed) {
        this.readed = readed;
    }

    public Notification() {

    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }

    public Post getPostNotification() {
        return postNotification;
    }

    public void setPostNotification(Post postNotification) {
        this.postNotification = postNotification;
    }

    public User getUserNotification() {
        return userNotification;
    }

    public void setUserNotification(User userNotification) {
        this.userNotification = userNotification;
    }

    public LikeDetails getLikeNotification() {
        return likeNotification;
    }

    public void setLikeNotification(LikeDetails likeNotification) {
        this.likeNotification = likeNotification;
    }

    public Comment getCommentNotification() {
        return commentNotification;
    }

    public void setCommentNotification(Comment commentNotification) {
        this.commentNotification = commentNotification;
    }

    public Friend getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Friend friendStatus) {
        this.friendStatus = friendStatus;
    }
}
