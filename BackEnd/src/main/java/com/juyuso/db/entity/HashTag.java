package com.juyuso.db.entity;

import lombok.Getter;

import javax.annotation.Nullable;
import javax.persistence.*;

@Getter
@Table(name = "hash_tag")
@Entity
public class HashTag {

    @Id
    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
    private String hashTag;
}
