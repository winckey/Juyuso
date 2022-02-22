package com.juyuso.db.entity.friend;

import com.juyuso.db.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {

    @Id
    @GeneratedValue
    @Column(name = "friend_request_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    private User fromUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_id", referencedColumnName = "id")
    private User toUser;

    public void addRequest(User from, User to) {
        this.toUser = to;
        toUser.getFriendRequests().add(this);
        this.fromUser = from;
    }


}
