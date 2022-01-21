package com.juyuso.api.service;

import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.api.dto.response.FriendResDto;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService {


    @Override
    public User search(FriendResDto friendResDto) {
        return null;
    }
}
