package com.juyuso.api.controller;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.api.dto.response.*;
import com.juyuso.api.service.FirebaseCloudMessageService;
import com.juyuso.api.service.FriendService;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.FriendRequest;
import com.juyuso.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@Validated
@Api(value = "친구 관리 api", tags = {"친구 관리"})
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @GetMapping()
    @ApiOperation(value = "친구 리스트 조회", notes = "<strong>친구리스트를 조회한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = FriendListResDto.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<FriendListResDto> findFriendList(@ApiIgnore Authentication authentication) {
        User userDetails = (User) authentication.getDetails();
        List<User> friendList = friendService.friendList(userDetails);


        List<User> requestList = friendService.RequestList(userDetails);


        return ResponseEntity.ok(FriendListResDto.of(200, "Success", friendList ,requestList));
    }

    @DeleteMapping()
    @ApiOperation(value = "친구 삭제", notes = "<strong>친구를 삭제한다. 삭제하고자 하는 유저 id</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> deleteFriend(@ApiIgnore Authentication authentication, @Valid @RequestBody FriendReqDto friendReqDto) {
        User userDetails = (User) authentication.getDetails();

        friendService.deleteFriend(userDetails, friendReqDto);

        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/request")
    @ApiOperation(value = "친구 추가 신청", notes = "<strong>친구를 추가신청을 한다. 신청하는 사람의 id</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> friendRequest(@ApiIgnore Authentication authentication, @Valid @RequestBody FriendReqDto friendReqDto) {
        User userDetails = (User) authentication.getDetails();
        FriendRequest friendRequest = friendService.addRequest(userDetails, friendReqDto);

        User toUser = friendRequest.getToUser();
        if (!toUser.getFcmToken().isEmpty()) {
            try {
                firebaseCloudMessageService.sendMessageTo(toUser.getFcmToken(), "친구 추가 요청", String.format("%s님께서 친구 추가 요청하셨습니다.", userDetails.getNickname()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/accept")
    @ApiOperation(value = "친구 신청 동의", notes = "<strong>친구를 추가신청을 한다. 신청 보낸사람의 사람의 id</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> friendAgree(@ApiIgnore Authentication authentication, @Valid @RequestBody FriendReqDto friendReqDto) {
        User userDetails = (User) authentication.getDetails();
        User fromUser = friendService.agreeRequest(friendReqDto, userDetails);

        if (!fromUser.getFcmToken().isEmpty()) {
            try {
                firebaseCloudMessageService.sendMessageTo(fromUser.getFcmToken(), "친구 추가 완료", String.format("%s님께서 친구 추가 요청을 수락하셨습니다.", userDetails.getNickname()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/reject")
    @ApiOperation(value = "친구 신청 거절", notes = "<strong>친구를 추가신청을 거절한다. 신청 보낸사람의 사람의 id</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> friendReject(@ApiIgnore Authentication authentication, @Valid @RequestBody FriendReqDto friendReqDto) {
        User userDetails = (User) authentication.getDetails();
        User fromUser = friendService.rejectRequest(friendReqDto, userDetails);

        if (!fromUser.getFcmToken().isEmpty()) {
            try {
                firebaseCloudMessageService.sendMessageTo(fromUser.getFcmToken(), "친구 추가 거절", String.format("%s님께서 친구 추가 요청을 거절하셨습니다.", userDetails.getNickname()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/info/{friendId}")
    @ApiOperation(value = "친구 정보 조회", notes = "<strong>친구정보를 조회한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정보 조회 성공", response = FriendResDto.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<FriendResDto> getFriendInfo(@NotNull @PathVariable Long friendId) {
        User user = friendService.getFriendInfo(friendId);
        return ResponseEntity.ok(FriendResDto.of(200, "Success", user));
    }

    @GetMapping("/{keyword}")
    @ApiOperation(value = "친구 검색", notes = "<strong>친구추가를 위한 전체 유저 검색.</strong>" +
            "<strong> keyword를 포함하는 별명을 가진 유저 검색")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = FriendSearchListResDto.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<FriendSearchListResDto> userSearch(@ApiIgnore Authentication authentication, @NotBlank @PathVariable String keyword) {

        User userDetails = (User) authentication.getDetails();
        List<User> userListMyFriend = friendService.userSearchMy(keyword , userDetails);
        List<User> userListNotFriend = friendService.userSearchNot(keyword , userDetails);
        return ResponseEntity.ok(FriendSearchListResDto.of(200, "Success", userListMyFriend , userListNotFriend));
    }

    @PostMapping("/ban")
    @ApiOperation(value = "차단 추가", notes = "<strong>친구를 차단하고 삭제 한다. 차단하고자하는 사람의 id</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> friendBan(@ApiIgnore Authentication authentication, @Valid @RequestBody FriendReqDto friendReqDto) {

        User userDetails = (User) authentication.getDetails();

        friendService.banRequest(userDetails, friendReqDto);
        friendService.deleteFriend(userDetails, friendReqDto);

        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/ban")
    @ApiOperation(value = "차단 취소", notes = "<strong>친구를 차단 취소 한다. 차단취소 하고자하는 사람의 id</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = BaseResponseBody.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<BaseResponseBody> friendBanCancel(@ApiIgnore Authentication authentication, @Valid @RequestBody FriendReqDto friendReqDto) {

        User userDetails = (User) authentication.getDetails();
        friendService.banCancelRequest(userDetails, friendReqDto);
        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/ban")
    @ApiOperation(value = "차단 리스트 조회", notes = "<strong>차단리스트를 조회한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = FriendBanResDto.class),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<FriendBanResDto> findBanList(@ApiIgnore Authentication authentication) {
        User userDetails = (User) authentication.getDetails();
        List<User> banList = friendService.banList(userDetails);

        return ResponseEntity.ok(FriendBanResDto.of(200, "Success", banList));
    }
}
