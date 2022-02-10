package com.juyuso.api.controller;

import com.juyuso.api.dto.response.ChatRoomResDto;
import com.juyuso.api.service.ChatRoomService;
import com.juyuso.api.service.FriendService;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Positive;


@Valid
@Api(value = "채팅 방 api", tags = {"채팅 방 관리"})
@RestController
@RequestMapping("/api/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final FriendService friendService;

    @GetMapping("/{friendId}")
    @ApiOperation(value = "채팅방 아이디 찾기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = ChatRoomResDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<ChatRoomResDto> chatHome(@ApiIgnore Authentication authentication, @Positive @PathVariable("friendId") Long friendId){
        User user = (User) authentication.getDetails();
        User friend = friendService.getFriendInfo(user, friendId);
        Long roomId = chatRoomService.getChatRoomId(user, friend);

        return ResponseEntity.ok(ChatRoomResDto.of(200, "Success", roomId));
    }

}
