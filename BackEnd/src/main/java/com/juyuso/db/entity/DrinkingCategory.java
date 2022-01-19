package com.juyuso.db.entity;

import com.juyuso.db.entity.enums.DrinkingKind;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrinkingCategory {


    @Id @GeneratedValue
    @JoinColumn(name = "drinking_id")
    private Long id;
    /// user 관계 추가 요구

    @Enumerated(EnumType.STRING)
    private DrinkingKind name; //READY, COMP

}
