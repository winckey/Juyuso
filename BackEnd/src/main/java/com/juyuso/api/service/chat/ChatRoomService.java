package com.juyuso.api.service.chat;

import com.juyuso.db.entity.user.User;
import com.juyuso.db.entity.chat.ChatRoom;

public interface ChatRoomService {
    Long getChatRoomId(User user , User friend);
    ChatRoom createNewRoom(User user , User friend);
}