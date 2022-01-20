package com.juyuso.db.entity;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
public class DrinkingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drinking_history_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private LocalDateTime drinkingTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "drinking_category_id")
    private DrinkingCategory drinkingCategory;

    private Long quantity;

}
