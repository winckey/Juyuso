package com.juyuso.api.controller;

import com.juyuso.api.dto.response.MeetingHistoryListResDto;
import com.juyuso.api.service.MeetingHistoryService;
import com.juyuso.api.service.MeetingService;
import com.juyuso.api.service.UserService;
import com.juyuso.db.entity.MeetingHistory;
import com.juyuso.db.entity.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<MeetingHistoryListResDto> getMeetingHistoryList(@PathVariable String userId) {
        List<MeetingHistory> list =  historyService.getMeetingHistoryList(userId);
        return MeetingHistoryListResDto.of(list);
    }


}
