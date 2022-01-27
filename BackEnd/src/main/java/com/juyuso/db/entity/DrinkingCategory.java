package com.juyuso.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class DrinkingCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drinking_category_id")
    private Long id;

    private String name;

}