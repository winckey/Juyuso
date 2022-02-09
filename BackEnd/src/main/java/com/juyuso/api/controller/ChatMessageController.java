package com.juyuso.api.controller;



import com.juyuso.api.dto.request.ChatMessageReqDto;
import com.juyuso.api.dto.response.ChatMessageResDto;
import com.juyuso.api.service.ChatMessageService;

import com.juyuso.db.entity.chat.Message;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final SimpMessagingTemplate template;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    public String sendMsgBroadcast(String username) {
        return "Hello, " + username;
    }

    @MessageMapping("/chat/message")
    public void sendMsgRoom(ChatMessageReqDto message) {

        chatMessageService.saveMessage(message);

        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    @ApiOperation(value = "체팅리스트 ", notes = "//http://localhost:8080/api/chat/message/1?page=0&size=5&sort=id,desc \n " +
                                                    " 다음과 같이 사용 ")
    @GetMapping("/api/chat/message/{roomId}")
    public ResponseEntity<ChatMessageResDto> getChatList(@NotBlank @PathVariable Long roomId ,@PageableDefault(size = 5 ,
                                                                                sort="id" , direction = Sort.Direction.DESC) Pageable pageable) {

       List<Message> messageList =  chatMessageService.getMessageList(roomId , pageable);
        return ResponseEntity.ok(ChatMessageResDto.of(200, "Success", messageList));
    }


}
