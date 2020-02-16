package org.socialMedia.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "hashtag")
public class Hashtag {

    @Id
    @GeneratedValue
    @Column(name = "HASHTAG_ID")
    private int hashtagID;

    @Column(name = "TEXT")
    @Lob
    private String text;

    @ManyToMany
    @JoinTable(name = "post_detail", joinColumns = @JoinColumn(name = "HASHTAG_ID"),
            inverseJoinColumns = @JoinColumn(name = "POST_ID")
    )
    private Collection<Post> listOfPosts = new ArrayList<Post>();


    public Hashtag() {
    }


    public Hashtag(String text) {
        this.text = text;
    }

    public int getHashtagID() {
        return hashtagID;
    }

    public void setHashtagID(int hashtagID) {
        this.hashtagID = hashtagID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Collection<Post> getListOfPosts() {
        return listOfPosts;
    }

    public void setListOfPosts(Collection<Post> listOfPosts) {
        this.listOfPosts = listOfPosts;
    }
}
