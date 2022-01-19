package com.juyuso.db.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "meeting_history")
public class MeetingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "id") // User 테이블의 pk
    private User user;

    @Column(name = "meeting_history_action")
    private String action;

//    @Column(name = "meeting_history_inserted_time")
//    private LocalDateTime insertedTime;
}
