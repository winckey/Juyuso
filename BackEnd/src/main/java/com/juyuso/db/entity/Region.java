package com.juyuso.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    private String name;

}
