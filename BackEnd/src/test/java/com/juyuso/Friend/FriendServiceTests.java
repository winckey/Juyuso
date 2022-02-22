package com.juyuso.Friend;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.api.dto.request.chat.ChatMessageReqDto;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.api.service.chat.ChatMessageService;
import com.juyuso.api.service.chat.ChatMessageServiceImpl;
import com.juyuso.api.service.firebase.FriendService;
import com.juyuso.api.service.firebase.FriendServiceImpl;
import com.juyuso.db.entity.chat.Message;
import com.juyuso.db.entity.friend.Friend;
import com.juyuso.db.entity.friend.FriendBan;
import com.juyuso.db.entity.friend.FriendRequest;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;



@ExtendWith(MockitoExtension.class)
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

        given(userRepository.findById(any())).willReturn(Optional.of(user2));
//        friendRepository.findByFromAndToId(user, friendId)
        given(friendRepository.findByFromAndToId(any(),any())).willReturn(Optional.of(new Friend()));
        //when
        FriendService friendService = new FriendServiceImpl(userRepository ,userQueryRepository
                                                                    ,friendRequestRepository,friendRepository , banRepository);
        User reUser = friendService.getFriendInfo(user1 , 2L);


        //then

        assertEquals( reUser.getId(), user2.getId());


    }

    @DisplayName("친구 차단")
    @Test
    public void banRequest() {

        //given
        User user1 = User.builder()
                .id(1L)
                .Bans(new ArrayList<>())
                .nickname("user1").build();
        User user2 = User.builder()
                .id(2L)
                .nickname("user2").build();

        FriendReqDto friendReqDto = new FriendReqDto(2L);

        given(userRepository.findById(any())).willReturn(Optional.of(user2));
        //when
        FriendService friendService = new FriendServiceImpl(userRepository ,userQueryRepository
                ,friendRequestRepository,friendRepository , banRepository);

        friendService.banRequest(user1 , friendReqDto);


        //then

        then(banRepository).should(times(1)).save(any());

    }


}
