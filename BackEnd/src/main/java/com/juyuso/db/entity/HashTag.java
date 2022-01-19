package com.juyuso.db.entity;

import lombok.Getter;

import javax.annotation.Nullable;
import javax.persistence.*;

@Getter
@Table(name = "hashtag")
@Entity
public class HashTag {

    @Id
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Column(name = "hashtag_tags")
    private String tags;
}
