package com.juyuso.api.service.firebase;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.db.entity.friend.FriendRequest;
import com.juyuso.db.entity.user.User;

import java.util.List;

public interface FriendService {
    User getFriendInfo(User user, Long friendId);
    FriendRequest addRequest(User from , FriendReqDto friendReqDto);
    void banRequest(User from , FriendReqDto friendReqDto);
    User acceptRequest(FriendReqDto friendReqDto , User userDetails);
    User rejectRequest(FriendReqDto friendReqDto , User to);
    void banCancelRequest(User userDetails, FriendReqDto friendReqDto);
    List<User> friendList(User user);
    List<User> banList(User user);
    List<User> RequestList(User user);
    void deleteFriend(User userDetails, FriendReqDto friendReqDto);
    List<User> userSearchMy(String keyword, User userDetails);
    List<User> userSearchNot(String keyword, User userDetails);
}