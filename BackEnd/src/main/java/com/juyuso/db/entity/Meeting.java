package com.juyuso.db.entity;


import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime callStartTime;

    private LocalDateTime callEndTime;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private boolean common;

    public Meeting(User owner, String title, String password, LocalDateTime callStartTime,
                   LocalDateTime callEndTime, boolean active, boolean common) {
        this.owner = owner;
        this.title = title;
        this.password = password;
        this.callStartTime = callStartTime;
        this.callEndTime = callEndTime;
        this.active = active;
        this.common = common;
    }

    
}
