package com.juyuso.db.repository;

import com.juyuso.db.entity.attend.Attendance;
import com.juyuso.db.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserAndIdRegDateBetween(User user, LocalDate start, LocalDate end);
    Optional<Attendance> findByUserAndIdRegDate(User user, LocalDate date);
}
