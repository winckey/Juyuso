package com.juyuso.api.service;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.db.entity.Friend;
import com.juyuso.db.entity.FriendRequest;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.FriendRepository;
import com.juyuso.db.repository.FriendRequestRepository;
import com.juyuso.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;
    @Override
    public User getFriendInfo(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public void addRequest(User from, FriendReqDto friendReqDto) {

        User to =  userRepository.findById(Long.parseLong(friendReqDto.getId())).get();

        System.out.println("----------- user id : " + to.getNickname());

        FriendRequest friendRequest = new FriendRequest();
        System.out.println("----------- from user id : " + from.getNickname());
        friendRequest.addRequest(from , to);
        friendRequestRepository.save(friendRequest);

    }

    @Override
    public void agreeRequest(FriendReqDto friendReqDto) {

        FriendRequest friendRequest = friendRequestRepository.findById(Long.parseLong(friendReqDto.getId())).get();

        User user1 = friendRequest.getFromUser();
        User user2 = friendRequest.getToUser();

        Friend friend = new Friend();
        friend.setUser(user1);
        friend.setFriend(user2);

        Friend friend2 = new Friend();
        friend2.setUser(user2);
        friend2.setFriend(user1);
        friendRequestRepository.delete(friendRequest);

        friendRepository.save(friend);
        friendRepository.save(friend2);
    }

    @Override
    public List<User> friendList(User user) {
        return  userRepository.findListByUserId(user.getId());
    }

    @Override
    public List<User> banList(User user) {
        return null;
    }

    @Override
    public List<User> RequestList(User user) {
        return  userRepository.findRequestListByUserId(user.getId());
    }

    @Override
    public List<User> userSearch(String keyword) {
        return  userRepository.findALLByNickname(keyword);
    }

    @Override
    @Transactional
    public void deleteFriend(User userDetails, FriendReqDto friendReqDto) {

        Long from = Long.parseLong(friendReqDto.getId());
        Long to   = (userDetails.getId());
        friendRepository.deleteBothByUserId(from  , to);
        friendRepository.deleteBothByUserId(to  , from);
    }


}
