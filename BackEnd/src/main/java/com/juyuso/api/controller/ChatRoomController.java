package com.juyuso.api.controller;

import com.juyuso.api.dto.response.ChatRoomResDto;
import com.juyuso.api.service.ChatRoomService;
import com.juyuso.api.service.FriendService;
import com.juyuso.db.entity.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@Api(value = "채팅 방 api")
@RestController
@RequestMapping("/api/chatRoom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final FriendService friendService;
    ////1 채팅방 생성
    @GetMapping("/{freindId}")
    public ResponseEntity<ChatRoomResDto> chatHome(@ApiIgnore Authentication authentication , @PathVariable Long freindId){

        System.out.println("========"+freindId);
        User user = (User) authentication.getDetails();
        User friend = friendService.getFriendInfo(freindId);
        Long roomId = chatRoomService.getChatRoomId(user , friend) ;

        return ResponseEntity.ok(ChatRoomResDto.of(200, "Success", roomId));
    }

}
