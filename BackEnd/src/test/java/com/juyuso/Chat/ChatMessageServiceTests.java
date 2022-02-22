package com.juyuso.Chat;

import com.juyuso.api.dto.request.chat.ChatMessageReqDto;
import com.juyuso.api.service.chat.ChatMessageService;
import com.juyuso.api.service.chat.ChatMessageServiceImpl;
import com.juyuso.db.entity.chat.ChatRoom;
import com.juyuso.db.entity.chat.ChatRoomUser;
import com.juyuso.db.entity.chat.Message;
import com.juyuso.db.entity.user.User;
import com.juyuso.db.repository.chat.ChatMessageRepository;
import com.juyuso.db.repository.chat.ChatRoomRepository;
import com.juyuso.db.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ChatMessageServiceTests {


    @Mock
    ChatRoomRepository chatRoomRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ChatMessageRepository chatMessageRepository;

    @Mock
    Pageable pageable;

    @Mock
    Message message;

    @Test
    void contextLoads() {
    }

    @DisplayName("채팅 생성")
    @Test
    public void setChatRoom() {

        //given
        ChatMessageReqDto chatMessageReqDto = new ChatMessageReqDto("1" , "1" ,"name" , "message" , "2");
        //given
        ChatMessageService chatMessageService = new ChatMessageServiceImpl(chatRoomRepository , userRepository , chatMessageRepository);


        //when

        chatMessageService.saveMessage(chatMessageReqDto);


        //then
        then(chatMessageRepository).should(times(1)).getById(any());
        then(message).should(times(1)).builder();

    }

    @DisplayName("채팅리스트 확인")
    @Test
    public void getMessageList() {

        //given
        ChatMessageService chatMessageService = new ChatMessageServiceImpl(chatRoomRepository , userRepository , chatMessageRepository);


        //when

        chatMessageService.getMessageList(1L , pageable);

        //then
        then(chatMessageRepository).should(times(1)).findAllByChatRoomId(any(),any());
    }
}
