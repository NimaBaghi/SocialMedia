package org.socialMedia.entities;


import javax.persistence.*;

@Entity
@Table(name = "like_detail")
public class LikeDetails {

    @Id
    @GeneratedValue
    @Column(name = "LIKE_ID")
    private int likeID;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userLiked;


    public LikeDetails() {
    }

    public int getLikeID() {
        return likeID;
    }

    public void setLikeID(int likeID) {
        this.likeID = likeID;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUserLiked() {
        return userLiked;
    }

    public void setUserLiked(User userLiked) {
        this.userLiked = userLiked;
    }
}
