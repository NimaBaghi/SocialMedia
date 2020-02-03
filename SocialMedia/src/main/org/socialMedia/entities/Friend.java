package org.socialMedia.entities;


import javax.persistence.*;

@Entity
@Table(name = "friend")
public class Friend {

    @Id
    @GeneratedValue
    @Column(name = "FRIEND_ID")
    private int friendID;

    @ManyToOne
    @JoinColumn(name = "FROM_ID")
    private User fromID;

    @ManyToOne
    @JoinColumn(name = "TO_ID")
    private User toID;

    @Column(name = "STATUS")
    private int status;

    public Friend() {
    }

    public Friend(int status){
        this.status = status;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setFriendID(int friendID) {
        this.friendID = friendID;
    }

    public User getFromID() {
        return fromID;
    }

    public void setFromID(User fromID) {
        this.fromID = fromID;
    }

    public User getToID() {
        return toID;
    }

    public void setToID(User toID) {
        this.toID = toID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
