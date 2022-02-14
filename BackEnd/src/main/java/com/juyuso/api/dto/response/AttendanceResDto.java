package com.juyuso.api.dto.response;

import com.juyuso.common.model.response.BaseResponseBody;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AttendanceResDto extends BaseResponseBody {

    private int year;
    private int month;
    private int count;
    private List<Attendance> attendanceList;

    @Data
    @AllArgsConstructor
    private static class Attendance {
        private String date;
    }

    public static AttendanceResDto of(Integer statusCode, String message, LocalDate date, List<com.juyuso.db.entity.attend.Attendance> list) {
        AttendanceResDto res = new AttendanceResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setYear(date.getYear());
        res.setMonth(date.getMonthValue());
        res.setCount(list.size());
        List<Attendance> newList = new ArrayList<>();
        list.forEach(item -> {
            newList.add(new Attendance(item.getId().getRegDate().toString()));
        });

        res.setAttendanceList(newList);

        return res;
    }
}
