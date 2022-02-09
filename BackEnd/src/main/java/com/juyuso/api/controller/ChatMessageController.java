package com.juyuso.api.controller;



import com.juyuso.api.dto.request.ChatMessageReqDto;
import com.juyuso.api.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.RestController;


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




}
