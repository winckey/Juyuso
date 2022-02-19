package com.juyuso.Chat;

import com.juyuso.db.entity.chat.ChatRoom;
import com.juyuso.db.entity.chat.ChatRoomUser;
import com.juyuso.db.entity.chat.Message;
import com.juyuso.db.entity.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MessageTests {

    @Test
    void contextLoads() {
    }

    @DisplayName("유저 채팅방 입장")
    @Test
    public void createMessage() {

        //given
        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setId(1L);
        User user = User.builder()
                .userId("userId")
                .build();


        String testMessage = "testMessage";

        //when
        Message message = Message.builder()
                .message(testMessage)
                .chatRoom(newChatRoom)
                .user(user)
                .build();


        //then
        assertEquals(message.getMessage() , testMessage);
        assertEquals(message.getUser().getId() , user.getId());
        assertEquals(message.getChatRoom() , newChatRoom);
    }



}
