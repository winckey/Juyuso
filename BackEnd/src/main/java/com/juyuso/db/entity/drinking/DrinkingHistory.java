package com.juyuso.db.entity.drinking;

import com.juyuso.db.entity.user.User;
import lombok.Getter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
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

    public DrinkingHistory() {

    }

    public DrinkingHistory(User user, LocalDateTime drinkingTime, DrinkingCategory drinkingCategory, Long quantity) {
        this.user = user;
        this.drinkingTime = drinkingTime;
        this.drinkingCategory = drinkingCategory;
        this.quantity = quantity;
    }

    public DrinkingHistory(Long id, User user, LocalDateTime drinkingTime, DrinkingCategory drinkingCategory, Long quantity) {
        this.id = id;
        this.user = user;
        this.drinkingTime = drinkingTime;
        this.drinkingCategory = drinkingCategory;
        this.quantity = quantity;
    }
}
