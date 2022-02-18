package com.juyuso.api.service.attend;

import com.juyuso.db.entity.attend.Attendance;
import com.juyuso.db.entity.attend.AttendanceId;
import com.juyuso.db.entity.user.User;
import com.juyuso.db.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Override
    public void createAttendance(User user) {
        // 현재 날짜 기준 출석체크가 이미 되어있는지 체크

        // 현재 날짜 기준 출석체크 안 되어 있으면 데이터 추가
        AttendanceId attendanceId = new AttendanceId();
        attendanceId.setRegDate(LocalDate.now());
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setRegDateTime(LocalDateTime.now());
        attendance.setId(attendanceId);

        attendanceRepository.save(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attendance> getAttendance(User user, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByUserAndIdRegDateBetween(user, startDate, endDate);
    }
}
