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
    @JoinColumn(name = "from_id" , referencedColumnName = "id")
    private User from;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    private User to;

    public void setFriend(User friend) {
        to.getFriends().add(this);
        this.from = friend;
    }

    public void setUser(User user) {
        this.to = user;
    }
}