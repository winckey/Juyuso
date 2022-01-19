package com.juyuso.db.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "meeting_img")
public class MeetingImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_img_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(name = "meeting_img_file_name")
    private String fileName;

    @Column(name = "file_size")
    private String fileSize;

    @Column(name = "file_url")
    private String fileUrl;

//    @Column(name = "meeting_img_reg_dt")
//    private LocalDateTime regDt;




}
