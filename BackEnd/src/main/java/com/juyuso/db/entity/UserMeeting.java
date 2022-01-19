package com.juyuso.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "user_meeting")
public class UserMeeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_meeting_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "id") // user 테이블의 pk
    private User user;

}
