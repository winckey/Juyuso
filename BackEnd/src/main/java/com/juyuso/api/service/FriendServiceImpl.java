package com.juyuso.api.service;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.FriendRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    @Override
    public User getFriendInfo(Long userId) {
        return friendRepository.findById(userId).get();
    }

    @Override
    public List<User> userSearch(String keyword) {
        return  friendRepository.findALLByNickname(keyword);
    }


}
