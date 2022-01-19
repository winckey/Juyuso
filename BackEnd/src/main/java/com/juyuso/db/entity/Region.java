package com.juyuso.db.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Region {
    @Id @GeneratedValue
    @Column(name = "region_id")
    private Long id;

    private String name;


}
