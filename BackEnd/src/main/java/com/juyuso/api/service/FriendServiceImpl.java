package com.juyuso.api.service;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.db.entity.Ban;
import com.juyuso.db.entity.Friend;
import com.juyuso.db.entity.FriendRequest;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.BanRepository;
import com.juyuso.db.repository.FriendRepository;
import com.juyuso.db.repository.FriendRequestRepository;
import com.juyuso.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;
    private final BanRepository banRepository;

    @Override
    public User getFriendInfo(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public void addRequest(User from, FriendReqDto friendReqDto) {

        User to = userRepository.findById(Long.parseLong(friendReqDto.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        //친구 요청이 있는가??
        FriendRequest check1FR = friendRequestRepository.findRequestByfromId(from.getId(), to.getId()).orElse(null);
        FriendRequest check2FR = friendRequestRepository.findRequestByfromId(to.getId(), from.getId()).orElse(null);
        if (check1FR != null || check2FR != null) {
            throw new CustomException(ErrorCode.FRIEND_REQUEST_DUPLICATE);
        }

        // 이미 친구인가?
        Friend checkFriend = friendRepository.findFriendByFromToId(from.getId(), to.getId()).orElse(null);
        if(checkFriend != null)
        {
            throw new CustomException(ErrorCode.FRIEND_DUPLICATE);
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.addRequest(from, to);
        friendRequestRepository.save(friendRequest);

    }

    @Override
    public void banRequest(User from, FriendReqDto friendReqDto) {
        User toBan = userRepository.findById(Long.parseLong(friendReqDto.getId()))
                                   .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Ban ban = new Ban();
        ban.addBan(from, toBan);
        banRepository.save(ban);
    }

    @Override
    public void agreeRequest(FriendReqDto friendReqDto, User to) {
        FriendRequest friendRequest = friendRequestRepository
                .findRequestByfromId(Long.parseLong(friendReqDto.getId()), to.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));;

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
        return userRepository.findListByUserId(user.getId());
    }

    @Override
    public List<User> banList(User user) {
        return userRepository.findBanListByUserId(user.getId());
    }

    @Override
    public List<User> RequestList(User user) {
        return userRepository.findRequestListByUserId(user.getId());
    }

    @Override
    public List<User> userSearch(String keyword) {
        return userRepository.findALLByNickname(keyword);
    }

    @Override
    @Transactional
    public void deleteFriend(User userDetails, FriendReqDto friendReqDto) {

        Long from = Long.parseLong(friendReqDto.getId());
        Long to = (userDetails.getId());
        friendRepository.deleteBothByUserId(from, to);
        friendRepository.deleteBothByUserId(to, from);
    }

    @Override
    public void rejectRequest(FriendReqDto friendReqDto, User to) {

        FriendRequest friendRequest = friendRequestRepository
                .findRequestByfromId(Long.parseLong(friendReqDto.getId()), to.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

        friendRequestRepository.delete(friendRequest);

    }

    @Override
    public void banCancelRequest(User userDetails, FriendReqDto friendReqDto) {
        Long from = Long.parseLong(friendReqDto.getId());
        Long to = (userDetails.getId());
        banRepository.deleteByBothUserId(from, to);
    }


    @Override
    public List<User> userSearchMy(String keyword, User userDetails) {
        return userRepository.findFriendByNickname(keyword , userDetails.getId());
    }

    @Override
    public List<User> userSearchNot(String keyword, User userDetails) {
        return userRepository.findNotFriendByNickname(keyword , userDetails.getId());
    }

}
