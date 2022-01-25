package com.juyuso.api.service;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.api.dto.response.FriendResDto;
import com.juyuso.db.entity.User;

import java.util.List;

public interface FriendService {
    User getFriendInfo(Long userId);

    void addRequest(User from , FriendReqDto friendReqDto);
    void agreeRequest(FriendReqDto friendReqDto);

    List<User> friendList(User user);
    List<User> banList(User user);
    List<User> RequestList(User user);
    List<User> userSearch(String keyword);


    void deleteFriend(User userDetails, FriendReqDto friendReqDto);
}