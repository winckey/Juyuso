package com.juyuso.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendRequest {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "from_id")
    private User fromUser;
    /// user 관계 추가 요구
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "to_id")
    private User toUser;
    /// user 관계 추가 요구




}
