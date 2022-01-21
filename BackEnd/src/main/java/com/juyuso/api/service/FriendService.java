package com.juyuso.api.service;

import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.api.dto.response.FriendResDto;
import com.juyuso.db.entity.User;

public interface FriendService {
    User search(FriendResDto friendResDto);

}