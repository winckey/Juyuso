package com.juyuso.api.service.firebase;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.db.entity.friend.FriendBan;
import com.juyuso.db.entity.friend.Friend;
import com.juyuso.db.entity.friend.FriendRequest;
import com.juyuso.db.entity.user.User;
import com.juyuso.db.repository.BanRepository;
import com.juyuso.db.repository.friend.FriendRepository;
import com.juyuso.db.repository.friend.FriendRequestRepository;
import com.juyuso.db.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;
    private final BanRepository banRepository;

    @Override
    @Transactional(readOnly = true)
    public User getFriendInfo(User user, Long friendId) {
        Optional<Friend> findFriend = friendRepository.findByFromAndToId(user, friendId);

        if (findFriend.isPresent()) {
            return userRepository.findById(friendId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        } else {
            throw new CustomException(ErrorCode.FRIEND_NOT_FOUND);
        }
    }

    @Override
    public FriendRequest addRequest(User from, FriendReqDto friendReqDto) {

        User to = userRepository.findById(friendReqDto.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 친구 요청이 있는가??
        Optional<FriendRequest> check1FR = friendRequestRepository.findById(from.getId(), to.getId());
        Optional<FriendRequest> check2FR = friendRequestRepository.findById(to.getId(), from.getId());
        if (check1FR.isPresent() || check2FR.isPresent()) {
            throw new CustomException(ErrorCode.FRIEND_REQUEST_DUPLICATE);
        }

        // 이미 친구인가?
        Optional<Friend> checkFriend = friendRepository.findByFromAndTo(from, to);
        if (checkFriend.isPresent()) {
            throw new CustomException(ErrorCode.FRIEND_DUPLICATE);
        }

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.addRequest(from, to);
        friendRequestRepository.save(friendRequest);

        return friendRequest;
    }

    @Override
    public void banRequest(User from, FriendReqDto friendReqDto) {
        User toBan = userRepository.findById(friendReqDto.getId())
                                   .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        FriendBan ban = new FriendBan();
        ban.addBan(from, toBan);
        banRepository.save(ban);
    }

    @Override
    public User acceptRequest(FriendReqDto friendReqDto, User to) {
        FriendRequest friendRequest = friendRequestRepository
                .findById(friendReqDto.getId(), to.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));;

        User fromUser = friendRequest.getFromUser();
        User toUser = friendRequest.getToUser();

        Friend friend = new Friend();
        friend.setUser(fromUser);
        friend.setFriend(toUser);

        Friend friend2 = new Friend();
        friend2.setUser(toUser);
        friend2.setFriend(fromUser);

        friendRequestRepository.delete(friendRequest);

        friendRepository.save(friend);
        friendRepository.save(friend2);

        return fromUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> friendList(User user) {
        return userRepository.findFriendListByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> banList(User user) {
        return userRepository.findFriendBanListByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> RequestList(User user) {
        return userRepository.findFriendRequestListByUser(user);
    }

    @Override
    public void deleteFriend(User userDetails, FriendReqDto friendReqDto) {
        Long from = friendReqDto.getId();
        Long to = userDetails.getId();

        friendRepository.deleteByFromIdAndToId(from, to);
        friendRepository.deleteByFromIdAndToId(to, from);
    }

    @Override
    public User rejectRequest(FriendReqDto friendReqDto, User to) {

        FriendRequest friendRequest = friendRequestRepository
                .findById(friendReqDto.getId(), to.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.FRIEND_REQUEST_NOT_FOUND));

        friendRequestRepository.delete(friendRequest);
        return friendRequest.getFromUser();
    }

    @Override
    public void banCancelRequest(User userDetails, FriendReqDto friendReqDto) {
        Long from = friendReqDto.getId();
        Long to = userDetails.getId();
        banRepository.deleteByBothUserId(from, to);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> userSearchMy(String keyword, User user) {
        return userRepository.findFriendListByNicknameAndUser(keyword, user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> userSearchNot(String keyword, User user) {
        return userRepository.findNotFriendListByNicknameAndUser(keyword, user);
    }

}
