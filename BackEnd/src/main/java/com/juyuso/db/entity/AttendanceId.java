package com.juyuso.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AttendanceId implements Serializable {

    private Long userId;

//    @Column(name = "att_id")
//    private Long id;

    private LocalDate regDate;

    @Override
    public int hashCode() {
        return Objects.hash(userId, regDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AttendanceId)) return false;

        AttendanceId other = (AttendanceId) obj;

        return this.userId.equals(other.userId) && this.regDate.equals(other.regDate);
    }
}
