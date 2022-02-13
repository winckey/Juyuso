package com.juyuso.api.controller;

import com.juyuso.api.dto.request.ChatMessageReqDto;
import com.juyuso.api.dto.response.ChatMessageResDto;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.api.service.ChatMessageService;

import com.juyuso.api.service.FirebaseCloudMessageService;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.User;
import com.juyuso.db.entity.chat.Message;
import com.juyuso.db.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

import java.io.IOException;
import java.util.List;

@Api(value = "채팅 메시지 api", tags = {"채팅 메시지 관리"})
@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessagingTemplate template;
    private final ChatMessageService chatMessageService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final UserRepository userRepository;

    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    public String sendMsgBroadcast(String username) {
        return "Hello, " + username;
    }

    @MessageMapping("/chat/message")
    public void sendMsgRoom(@Validated ChatMessageReqDto message) {
        Long receiverId = Long.parseLong(message.getReceiverId());
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!receiver.getFcmToken().isEmpty()) {
            try {
                firebaseCloudMessageService.sendMessageTo(receiver.getFcmToken(), message.getWriterName(),
                        message.getMessage().length() > 10 ?
                                String.format("[%s] : %10s...", message.getWriterName(), message.getMessage())
                                : String.format("[%s] : %s", message.getWriterName(), message.getMessage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        chatMessageService.saveMessage(message);

        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    @GetMapping("/api/chat/message/{roomId}")
    @ApiOperation(value = "채팅 메시지 리스트", notes = "http://localhost:8080/api/chat/message/1?page=0&size=5&sort=id,desc \n " +
            " 다음과 같이 사용 ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = ChatMessageResDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<ChatMessageResDto> getChatList(@Positive @PathVariable("roomId") Long roomId,
                                                         @PageableDefault(size = 5, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
       List<Message> messageList =  chatMessageService.getMessageList(roomId, pageable);
        return ResponseEntity.ok(ChatMessageResDto.of(200, "Success", messageList));
    }
}
