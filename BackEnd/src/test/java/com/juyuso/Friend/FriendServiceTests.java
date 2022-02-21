package com.juyuso.Friend;

import com.juyuso.api.dto.request.chat.ChatMessageReqDto;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.api.service.chat.ChatMessageService;
import com.juyuso.api.service.chat.ChatMessageServiceImpl;
import com.juyuso.api.service.firebase.FriendService;
import com.juyuso.api.service.firebase.FriendServiceImpl;
import com.juyuso.db.entity.chat.Message;
import com.juyuso.db.entity.friend.Friend;
import com.juyuso.db.entity.user.User;
import com.juyuso.db.repository.BanRepository;
import com.juyuso.db.repository.chat.ChatMessageRepository;
import com.juyuso.db.repository.chat.ChatRoomRepository;
import com.juyuso.db.repository.friend.FriendRepository;
import com.juyuso.db.repository.friend.FriendRequestRepository;
import com.juyuso.db.repository.user.UserQueryRepository;
import com.juyuso.db.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


class FriendServiceTests {



    @Mock
    UserRepository userRepository;

    @Mock
    UserQueryRepository userQueryRepository;

    @Mock
    FriendRequestRepository friendRequestRepository;

    @Mock
    FriendRepository friendRepository;

    @Mock
    BanRepository banRepository;


    @Test
    void contextLoads() {




    }


    @DisplayName("친구 정보 확인")
    @Test
    public void getFriendInfo() {

        //given
        User user1 = User.builder()
                .id(1L)
                .nickname("user1").build();
        User user2 = User.builder()
                .id(2L)
                .nickname("user2").build();

        given(userRepository.findById(2L)).willReturn(Optional.of(user2));

        given(friendRepository.findById(2L)).willReturn(Optional.of(new Friend()));
        //when
        FriendService friendService = new FriendServiceImpl(userRepository ,userQueryRepository
                                                                    ,friendRequestRepository,friendRepository , banRepository);
        User reUser = friendService.getFriendInfo(user1 , 2L);


        //then

        assertEquals( reUser.getId(), user2.getId());


    }

}
