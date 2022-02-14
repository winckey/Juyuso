package com.juyuso.db.entity.meeting;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.juyuso.db.entity.user.User;
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
    @JsonBackReference
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String action;

    private LocalDateTime insertedTime;

    public MeetingHistory () {

    }

    public MeetingHistory (Meeting meeting, User user, String action) {
        this.meeting = meeting;
        this.user = user;
        this.action = action;
        this.insertedTime = LocalDateTime.now();
    }


}
