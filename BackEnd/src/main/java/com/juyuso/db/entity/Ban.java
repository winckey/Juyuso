package com.juyuso.db.entity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Ban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ban_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User User;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ban_user_id" , referencedColumnName = "id")
    private User BanUser;

}
