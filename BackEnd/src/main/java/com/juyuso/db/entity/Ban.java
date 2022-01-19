package com.juyuso.db.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ban {


    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User User;
    /// user 관계 추가 요구
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ban_id" , referencedColumnName = "user_id")
    private User BanUser;
    /// user 관계 추가 요구

}
