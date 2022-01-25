package com.juyuso.db.entity;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_id" , referencedColumnName = "id")
    private User friend;


    public void setFriend(User friend) {
        user.getFriends().add(this);
        this.friend = friend;
    }

    public void setUser(User user) {
        this.user = user;
    }
}