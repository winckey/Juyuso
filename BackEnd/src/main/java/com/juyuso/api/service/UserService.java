package com.juyuso.api.service;

import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.db.entity.Users;

public interface UserService {
    Users createUser(RegisterReqDto userRegisterInfo);
    Users getUserByUserId(String userId);
}