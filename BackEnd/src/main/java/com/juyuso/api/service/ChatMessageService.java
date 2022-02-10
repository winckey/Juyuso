package com.juyuso.api.service;

import com.juyuso.api.dto.request.ChatMessageReqDto;
import com.juyuso.db.entity.User;
import com.juyuso.db.entity.chat.ChatRoom;
import com.juyuso.db.entity.chat.Message;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ChatMessageService {
    void saveMessage(ChatMessageReqDto chatMessageReqDto);
    List<Message> getMessageList(Long RoomId , Pageable pageable);
}