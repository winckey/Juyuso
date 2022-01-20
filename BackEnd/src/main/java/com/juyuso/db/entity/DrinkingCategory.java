package com.juyuso.db.entity;

import javax.persistence.*;

@Entity
public class DrinkingCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drinking_category_id")
    private Long id;

    private String name;

}