package com.juyuso.api.controller;

import com.juyuso.api.dto.response.MeetingHistoryListResDto;
import com.juyuso.api.service.MeetingHistoryService;
import com.juyuso.api.service.MeetingService;
import com.juyuso.api.service.UserService;
import com.juyuso.db.entity.MeetingHistory;
import com.juyuso.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.mappers.ModelMapperImpl;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "유저 미팅 방문 기록")
@RequestMapping("/api/history")
@RestController
public class MeetingHistoryController {

    private final MeetingService meetingService;
    private final UserService userService;
    private final MeetingHistoryService historyService;

    @Autowired
    public MeetingHistoryController(MeetingService meetingService, UserService userService,
                                    MeetingHistoryService historyService) {
        this.meetingService = meetingService;
        this.userService = userService;
        this.historyService = historyService;
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "미팅 접속 기록 보기" , notes = "<strong>미팅 접속기록 </strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "방 접속기록"),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<List<MeetingHistoryListResDto>> getMeetingHistoryList(@PathVariable String userId) {
        List<MeetingHistory> list =  historyService.getMeetingHistoryList(userId);
        return ResponseEntity.ok(MeetingHistoryListResDto.of(list));
    }


}
