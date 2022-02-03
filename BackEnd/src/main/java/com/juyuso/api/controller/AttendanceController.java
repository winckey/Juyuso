package com.juyuso.api.controller;

import com.juyuso.api.dto.response.AttendanceResDto;
import com.juyuso.api.service.AttendanceService;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.Attendance;
import com.juyuso.db.entity.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.util.List;

@Api(tags = {"출석 체크"})
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping()
    @ApiOperation(value = "출석 체크", notes = "오늘 날짜를 기준으로 출석 체크한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> createAttendance(@ApiIgnore Authentication authentication) {
        User user = (User) authentication.getDetails();
        attendanceService.createAttendance(user);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping()
    @ApiOperation(value = "이번 달 출석체크", notes = "이번 달의 출석체크 일자를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getAttendance(@ApiIgnore Authentication authentication) {
        User user = (User) authentication.getDetails();
        LocalDate date = LocalDate.now();
        List<Attendance> list = attendanceService.getAttendance(user, date.withDayOfMonth(1), date.withDayOfMonth(date.lengthOfMonth()));
        return ResponseEntity.status(200).body(AttendanceResDto.of(200, "Success", date, list));
    }

    @GetMapping("/{year}/{month}")
    @ApiOperation(value = "지정 년월 출석체크", notes = "요청한 년, 월의 출석체크 일자를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> getAttendanceByDate(
            @ApiIgnore Authentication authentication,
            @PathVariable int year,
            @PathVariable int month
    ) {
        User user = (User) authentication.getDetails();
        LocalDate date = LocalDate.of(year, month, 1);
        List<Attendance> list = attendanceService.getAttendance(user, date.withDayOfMonth(1), date.withDayOfMonth(date.lengthOfMonth()));
        return ResponseEntity.status(200).body(AttendanceResDto.of(200, "Success", date, list));
    }
}
