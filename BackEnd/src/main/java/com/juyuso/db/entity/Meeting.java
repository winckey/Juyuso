package com.juyuso.db.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "meeting")
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @Column(name = "meeting_title", nullable = false)
    private String title;

    @Column(name = "meeting_password", nullable = false)
    private String password;

//    @Column(name = "meeting_title", nullable = false)
//    private LocalDateTime callStartTime;
//
//    @Column(nullable = false)
//    private LocalDateTime callEndTime;

    @Column(name = "meeting_is_active", nullable = false)
    private boolean isActive;

    @Column(name = "meeting_is_public", nullable = false)
    private boolean isPublic;











}
