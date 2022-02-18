package com.juyuso.api.service.attend;

import com.juyuso.db.entity.attend.Attendance;
import com.juyuso.db.entity.user.User;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {
    void createAttendance(User user);
    List<Attendance> getAttendance(User user, LocalDate startDate, LocalDate endDate);
}
