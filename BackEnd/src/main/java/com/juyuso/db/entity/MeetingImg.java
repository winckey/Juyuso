package com.juyuso.db.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class MeetingImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_img_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column
    private String fileName;

    @Column
    private String fileSize;

    @Column
    private String fileContentType;

    @Column
    private String fileUrl;

}
