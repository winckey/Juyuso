package com.juyuso.api.controller;



import com.juyuso.api.dto.request.ChatMessageReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChatMessageController {

    private final SimpMessagingTemplate template;

    @Autowired
    public ChatMessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/broadcast")
    @SendTo("/topic/messages")
    public String send(String username) {
        return "Hello, " + username;
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageReqDto message) {
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

}
