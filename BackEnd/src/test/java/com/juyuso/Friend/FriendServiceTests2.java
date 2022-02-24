package com.juyuso.Friend;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.service.firebase.FriendService;
import com.juyuso.api.service.firebase.FriendServiceImpl;
import com.juyuso.db.entity.friend.Friend;
import com.juyuso.db.entity.friend.FriendRequest;
import com.juyuso.db.entity.user.User;
import com.juyuso.db.repository.BanRepository;
import com.juyuso.db.repository.friend.FriendRepository;
import com.juyuso.db.repository.friend.FriendRequestRepository;
import com.juyuso.db.repository.user.UserQueryRepository;
import com.juyuso.db.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class FriendServiceTests2 {



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
    @DisplayName("친구 거절")
    @Test
    public void rejectRequest() {

        //given
        User user1 = User.builder()
                .id(1L)
                .nickname("user1").build();
        User user2 = User.builder()
                .id(2L)
                .nickname("user2").build();

        given(friendRequestRepository.findById(2L,user1.getId())).willReturn(Optional.of(new FriendRequest(1L , user1 , user2)));

        //when
        FriendService friendService = new FriendServiceImpl(userRepository ,userQueryRepository
                ,friendRequestRepository,friendRepository , banRepository);

        //then
        friendService.rejectRequest(new FriendReqDto(2L) , user1);

        then(friendRequestRepository).should(times(1)).delete(any());


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
        FriendService friendService = new FriendServiceImpl(userRepository ,userQueryRepository
                                                                    ,friendRequestRepository,friendRepository , banRepository);

        //then

        try {
            User reUser = friendService.getFriendInfo(user1, 2L);
            assertEquals( reUser.getId(), user2.getId());
        }catch (CustomException e){
            System.out.println("친구 없음");
        }




    }


}
