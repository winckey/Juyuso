package com.juyuso.api.service.chat;

import com.juyuso.api.dto.request.chat.ChatMessageReqDto;
import com.juyuso.db.entity.chat.Message;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ChatMessageService {
    void saveMessage(ChatMessageReqDto chatMessageReqDto);
    List<Message> getMessageList(Long RoomId , Pageable pageable);
}