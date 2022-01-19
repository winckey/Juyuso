package com.juyuso.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {


    @Id @GeneratedValue
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    /// user 관계 추가 요구


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_id" , referencedColumnName = "user_id")
    private User friend;


}
