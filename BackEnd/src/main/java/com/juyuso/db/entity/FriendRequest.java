package com.juyuso.db.entity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class FriendRequest {

    @Id @GeneratedValue
    @Column(name = "friend_request_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_id" , referencedColumnName = "id")
    private User fromUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_id" , referencedColumnName = "id")
    private User toUser;

}
