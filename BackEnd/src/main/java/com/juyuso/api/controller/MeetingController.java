package com.juyuso.api.controller;

import com.juyuso.api.dto.request.DrinkingHistoryAddReqDto;
import com.juyuso.api.dto.request.meeting.MeetingCreateReqDto;
import com.juyuso.api.dto.request.meeting.MeetingModifyReqDto;
import com.juyuso.api.dto.response.meeting.*;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.api.service.drinking.DrinkingHistoryService;
import com.juyuso.api.service.meeting.MeetingHistoryService;
import com.juyuso.api.service.meeting.MeetingService;
import com.juyuso.api.service.user.UserService;
import com.juyuso.db.entity.meeting.MeetingHistory;
import com.juyuso.db.entity.user.User;
import io.openvidu.java.client.OpenVidu;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "미팅방 관리 api", tags = {"미팅방 관리"})
@RestController
@RequestMapping("/api/meeting")
public class MeetingController {

    private final MeetingService meetingService;
    private final UserService userService;
    private final MeetingHistoryService historyService;
    private OpenVidu openVidu;
    private final DrinkingHistoryService drinkingHistoryService;

    private int LIMIT_MEETING = 10; // 인원 제한

    private String OPENVIDU_URL;
    private String SECRET;

    @Autowired
    public MeetingController(MeetingService meetingService, @Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl,
                             UserService userService, MeetingHistoryService historyService,
                             DrinkingHistoryService drinkingHistoryService)
    {
        this.meetingService = meetingService;
        this.userService = userService;
        this.historyService = historyService;
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
        this.drinkingHistoryService = drinkingHistoryService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "미팅방 만들기", notes = "<strong>방만들기</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "방만들기 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<MeetingCreateResDto> createMeeting(@RequestBody @ApiParam(value = "방만들기 정보") MeetingCreateReqDto reqDto, Principal principal) {
        String userId = principal.getName();
        Long meetingId = meetingService.createMeeting(reqDto, userId);
        historyService.saveMeetingHistory(meetingId, userId, "생성");
        return ResponseEntity.ok(MeetingCreateResDto.of(meetingId, reqDto.getMeetingName(), reqDto.getMeetingPassword(), userId));
    }

    @GetMapping("/search")
    @ApiOperation(value = "미팅방 리스트" , notes = "<strong>방 리스트 </strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "검색성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<Page<MeetingListResDto>> getMeetingListByParam(
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Boolean common,
            @PageableDefault(size = 12) Pageable pageable)
    {
        if(tags != null) {
            if(common == null) {
                return ResponseEntity.ok(MeetingListResDto.of(meetingService.findAllByTags(tags, pageable)));
            }
            else {
                return ResponseEntity.ok(MeetingListResDto.of(meetingService.findAllByTagContainingAndCommon(tags, pageable, common)));
            }

        }
        else if(title != null){
            if(common == null) {
                return ResponseEntity.ok(MeetingListResDto.of(meetingService.findAllByTitleContaining(title, pageable)));
            }
            else {
                return ResponseEntity.ok(MeetingListResDto.of(meetingService.findAllByTitleContainingAndCommon(title, pageable, common)));
            }

        }
        else if(tags == null && title == null) {
            if(common != null) {
                return ResponseEntity.ok(MeetingListResDto.of(meetingService.findAllByCommon(pageable, common)));
            }
        }
        return ResponseEntity.ok(MeetingListResDto.of(meetingService.findAll(pageable)));

    }

    @PostMapping("/enter/{meetingId}")
    @ApiOperation(value = "미팅방 들어가기" , notes = "<strong>방 들어가기 </strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "방 들어가기"),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<MeetingEnterResDto> enterMeeting(@PathVariable Long meetingId, Principal principal) {
        String userId = principal.getName();
        int cnt = meetingService.findByMeetingId(meetingId).getCnt();
        if (cnt >= LIMIT_MEETING) {
            throw new CustomException(ErrorCode.CANNOT_ENTER_MEETING);
        }
        else {
             historyService.saveMeetingHistory(meetingId, userId, "입장");
             meetingService.plusCnt(meetingId);
         }

        return ResponseEntity.ok(MeetingEnterResDto.of(meetingId, cnt));
    }

    @PostMapping("/leave/{meetingId}")
    @ApiOperation(value = "미팅방 나가기" , notes = "<strong>방 나가기 </strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "방 나가기 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<MeetingLeaveResDto> leaveMeeting (@PathVariable Long meetingId, Principal principal,
                                                            @RequestBody DrinkingHistoryAddReqDto reqDto) {

        int cnt = meetingService.findByMeetingId(meetingId).getCnt();
        String userId = principal.getName();
        User user = userService.getUserByUserId(userId);
        if(user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        historyService.saveMeetingHistory(meetingId, userId, "퇴장");
        drinkingHistoryService.addDrinking(reqDto, user);

        if(cnt == 1) {
            meetingService.changeActiveMeetingByMeetingId(meetingId);
            //한명남았을때 active false 로 바꿈
        } else {
            meetingService.minusCnt(meetingId);
        }
        return ResponseEntity.ok(MeetingLeaveResDto.of(meetingId));
    }

    @PatchMapping("/modify/{meetingId}")
    @ApiOperation(value = "미팅 테마 변경 " , notes = "<strong>미팅 테마기록 </strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "방 테마변경"),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<MeetingModifyResDto> modifyMeeting (@PathVariable Long meetingId, @RequestBody MeetingModifyReqDto reqDto) {
        return ResponseEntity.ok(MeetingModifyResDto.of(meetingService.modifyMeeting(meetingId, reqDto)));
    }



    @GetMapping("history/{userId}")
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
