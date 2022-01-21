package com.juyuso.api.service;

import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.RegionRepository;
import com.juyuso.db.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository usersRepository;
    private final RegionRepository regionRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository usersRepository, RegionRepository regionRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.regionRepository = regionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(RegisterReqDto registerRequestDto) {
        User userEntity = registerRequestDto.toEntity();
        userEntity.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        userEntity.setRegion(regionRepository.getById(registerRequestDto.getRegionId()));
        return usersRepository.save(userEntity);
    }

    @Override
    public User getUserByUserId(String userId) {
        // Optional 용법 체크 필요 (warning 참조)
        return usersRepository.findByUserId(userId).get();
    }
}
