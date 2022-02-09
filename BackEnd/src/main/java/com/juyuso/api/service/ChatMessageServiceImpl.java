package com.juyuso.api.service;

import com.juyuso.api.dto.request.ChatMessageReqDto;
import com.juyuso.db.entity.User;
import com.juyuso.db.entity.chat.ChatRoom;
import com.juyuso.db.entity.chat.Message;
import com.juyuso.db.repository.ChatMessageRepository;
import com.juyuso.db.repository.ChatRoomRepository;
import com.juyuso.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;


    @Override
    public void saveMessage(ChatMessageReqDto message) {

        ChatRoom chatRoom = chatRoomRepository.getById(Long.parseLong(message.getChatRoomId()));
        User user = userRepository.getById(Long.parseLong(message.getWriterId()));
        Message chatMessage = Message.builder()
                .message(message.getMessage())
                .chatRoom(chatRoom)
                .user(user)
                .build();

        chatMessageRepository.save(chatMessage);
        
    }

    @Override
    public List<Message> getMessageList(Long roomId , Pageable  pageable) {



        return chatMessageRepository.findAllByChatRoomId(roomId , pageable).get();
    }
}