package com.juyuso.api.controller;

import com.juyuso.api.dto.request.MeetingCreateReqDto;
import com.juyuso.api.dto.response.FriendResDto;
import com.juyuso.api.dto.response.LoginResDto;
import com.juyuso.api.dto.response.MeetingCreateResDto;
import com.juyuso.api.service.FriendService;
import com.juyuso.api.service.MeetingService;
import com.juyuso.api.service.UserService;
import com.juyuso.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "친구 관리 api")
@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {



    private FriendService friendService;
    @GetMapping()
    @ApiOperation(value = "친구 리스트 조회", notes = "<strong>친구리스트를 조회한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트조회 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<FriendResDto> findFriendList(@RequestBody FriendResDto reqDto) {

        User user = null;

        return ResponseEntity.ok(FriendResDto.of(200, "Success", user ));
    }



    @GetMapping("/info/{freindId}")
    @ApiOperation(value = "친구 정보 조회", notes = "<strong>친구정보를 조회한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정보조회 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<FriendResDto> findFriend(@RequestBody FriendResDto reqDto) {
        User user = null;
        return ResponseEntity.ok(FriendResDto.of(200, "Success", user ));
    }



    @GetMapping("/{keyword}")
    @ApiOperation(value = "유저 검색", notes = "<strong>친구추가를 위한 전체 유저 검색.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트조회 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<FriendResDto> userSearch(@RequestBody FriendResDto reqDto) {

        User user = null;

        return ResponseEntity.ok(FriendResDto.of(200, "Success", user ));
    }



}
