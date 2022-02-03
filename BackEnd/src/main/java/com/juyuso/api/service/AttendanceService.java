package com.juyuso.api.service;

import com.juyuso.db.entity.Attendance;
import com.juyuso.db.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    void createAttendance(User user);
    List<Attendance> getAttendance(User user, LocalDate startDate, LocalDate endDate);
}
