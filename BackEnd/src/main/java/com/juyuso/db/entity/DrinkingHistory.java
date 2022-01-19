package com.juyuso.db.entity;

import com.juyuso.db.entity.enums.DrinkingKind;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrinkingHistory {


    @Id @GeneratedValue
    @Column(name = "drinking_id")
    private Long id;



    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    /// user 관계 추가 요구

    private LocalDateTime drinkingDate; //주문시간


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "drinking_id")
    private DrinkingCategory drinkingId;
    /// user 관계 추가 요구



    private Long quantity;  //주문량



}
