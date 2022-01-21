package com.juyuso.db.entity;

import javax.persistence.*;

@Entity
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hash_tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    private String tag;

    public HashTag (Meeting meeting, String tag) {
        this.meeting = meeting;
        this.tag = tag;
    }


}
