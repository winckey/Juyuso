package com.juyuso.api.service;

import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.db.entity.User;

public interface UserService {
    User createUser(RegisterReqDto userRegisterInfo);
    User getUserByUserId(String userId);
}