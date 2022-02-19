package com.juyuso.Chat;

import com.juyuso.db.entity.chat.ChatRoom;
import com.juyuso.db.entity.chat.ChatRoomUser;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;


class ChatRoomUserTests {

    @Test
    void contextLoads() {
    }

    @DisplayName("유저 채팅방 입장")
    @Test
    public void setChatRoom() {

        //given
        ChatRoom newChatRoom = new ChatRoom();
        ChatRoomUser chatRoomUser = new ChatRoomUser();
        //when

        chatRoomUser.setChatRoom(newChatRoom);

        //then
        assertEquals(chatRoomUser.getChatRoom() , newChatRoom);
        assertEquals(newChatRoom.getChatRoomUsers().get(0) , chatRoomUser);
    }



}
