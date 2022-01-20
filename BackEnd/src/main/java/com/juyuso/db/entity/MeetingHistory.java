package com.juyuso.db.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class MeetingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String action;

    private LocalDateTime insertedTime;
}
