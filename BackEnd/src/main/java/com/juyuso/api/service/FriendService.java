package com.juyuso.api.service;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.db.entity.FriendRequest;
import com.juyuso.db.entity.User;

import java.util.List;

public interface FriendService {
    User getFriendInfo(Long userId);

    FriendRequest addRequest(User from , FriendReqDto friendReqDto);
    void banRequest(User from , FriendReqDto friendReqDto);

    User acceptRequest(FriendReqDto friendReqDto , User userDetails);

    List<User> friendList(User user);
    List<User> banList(User user);
    List<User> RequestList(User user);
    List<User> userSearch(String keyword);


    void deleteFriend(User userDetails, FriendReqDto friendReqDto);

    User rejectRequest(FriendReqDto friendReqDto , User to);

    void banCancelRequest(User userDetails, FriendReqDto friendReqDto);


    List<User> userSearchMy(String keyword, User userDetails);

    List<User> userSearchNot(String keyword, User userDetails);
}