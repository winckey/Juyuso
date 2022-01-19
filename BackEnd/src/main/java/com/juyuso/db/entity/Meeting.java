package com.juyuso.db.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime callStartTime;

    @Column(nullable = false)
    private LocalDateTime callEndTime;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private boolean isPublic;

    @OneToMany(mappedBy = "id")
    List<HashTag> list = new ArrayList<>();









}
