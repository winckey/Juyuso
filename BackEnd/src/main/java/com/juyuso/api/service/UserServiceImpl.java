package com.juyuso.api.service;

import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.db.entity.Users;
import com.juyuso.db.repository.UsersRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository usersRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users createUser(RegisterReqDto registerRequestDto) {
        Users userEntity = registerRequestDto.toEntity();
        userEntity.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        return usersRepository.save(userEntity);
    }

    @Override
    public Users getUserByUserId(String userId) {
        // Optional 용법 체크 필요 (warning 참조)
        return usersRepository.findByUserId(userId).get();
    }
}
