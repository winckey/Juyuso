package com.juyuso.api.controller;

import com.juyuso.api.dto.request.MeetingCreateReqDto;
import com.juyuso.api.dto.response.MeetingCreateResDto;
import com.juyuso.api.service.MeetingService;
import com.juyuso.api.service.UserService;
import com.juyuso.db.entity.Meeting;
import io.openvidu.java.client.OpenVidu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "미팅방 관리 api")
@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    private final MeetingService meetingService;
    private final UserService userService;
    private OpenVidu openVidu;

    private Map<String, Integer> mapSessions = new ConcurrentHashMap<>();



    private String OPENVIDU_URL;
    private String SECRET;

    @Autowired
    public MeetingController(MeetingService meetingService, @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl, UserService userService){
        this.meetingService = meetingService;
        this.userService = userService;
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }


    @PostMapping("/create")
    @ApiOperation(value = "미팅방 만들기", notes = "<strong>방만들기</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "방만들기 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public MeetingCreateResDto createMeeting(@RequestBody MeetingCreateReqDto reqDto, Principal principal) {
        String userId = principal.getName();
        meetingService.createMeeting(reqDto, userId);
        return MeetingCreateResDto.of(reqDto.getMeetingName(), reqDto.getMeetingPassword(), userId);
    }

    @GetMapping("/search")
    @ApiOperation(value = "미팅방 리스트" , notes = "<strong>방 리스트 </strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "검색성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity getMeetingListByTag(@RequestParam(required = false) String tags,
                                              @RequestParam(required = false) String title, @PageableDefault(size = 12) Pageable pageable) {
        if(tags != null) {
            Page<Meeting> list = meetingService.findAllByTag(tags, pageable);
            return new ResponseEntity(list, HttpStatus.OK);
        }
        else if(title != null){
            return new ResponseEntity(meetingService.findAllByTitle(title, pageable), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(meetingService.findAll(pageable), HttpStatus.OK);
        }

    }


}
