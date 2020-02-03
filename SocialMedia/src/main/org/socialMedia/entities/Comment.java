package org.socialMedia.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ID")
    private int commentID;

    @Column(name = "CONTEXT")
    private String context;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User userComment;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post postComment;

    public Comment() {
    }

    public Comment(String context) {
        this.context = context;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public User getUserComment() {
        return userComment;
    }

    public void setUserComment(User userComment) {
        this.userComment = userComment;
    }

    public Post getPostComment() {
        return postComment;
    }

    public void setPostComment(Post postComment) {
        this.postComment = postComment;
    }
}
