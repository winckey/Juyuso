package com.juyuso.db.entity.meeting;


import com.juyuso.db.entity.HashTag;
import com.juyuso.db.entity.user.User;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(nullable = false)
    private String url;

    @ColumnDefault("0")
    private int cnt;

    @OneToMany(mappedBy = "meeting")
    private List<HashTag> hashtags = new ArrayList<>();

    public Meeting() {

    }

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

    public Meeting(User owner, String title, String password, LocalDateTime callStartTime,
                   LocalDateTime callEndTime, boolean active, boolean common, String url, int cnt) {
        this.owner = owner;
        this.title = title;
        this.password = password;
        this.callStartTime = callStartTime;
        this.callEndTime = callEndTime;
        this.active = active;
        this.common = common;
        this.url = url;
        this.cnt = cnt;
    }

    public void changeActive() {
        this.active = false;
    }

    public static List<String> toList(Meeting meeting) {
        List<String> list = new ArrayList<>();
        for(int i = 0; i < meeting.getHashtags().size(); i++) {
            list.add(meeting.getHashtags().get(i).getTag());
        }
        return list;
    }

    public void modifyUrl(String url) {
        this.url = url;
    }

}
