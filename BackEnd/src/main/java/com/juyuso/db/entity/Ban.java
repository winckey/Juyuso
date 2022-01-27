package com.juyuso.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Ban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ban_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ban_user_id" , referencedColumnName = "id")
    private User BanUser;

    public void addBan(User from, User toBan) {
        this.user = from;
        user.getBans().add(this);
        this.BanUser = toBan;

    }
}
