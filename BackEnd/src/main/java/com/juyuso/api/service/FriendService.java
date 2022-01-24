package com.juyuso.api.service;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.api.dto.response.FriendResDto;
import com.juyuso.db.entity.User;

import java.util.List;

public interface FriendService {
    User getFriendInfo(Long userId);


    List<User> userSearch(String keyword);


}