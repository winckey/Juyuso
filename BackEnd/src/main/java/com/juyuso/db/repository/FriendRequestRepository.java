package com.juyuso.db.repository;

import com.juyuso.db.entity.Friend;
import com.juyuso.db.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

}
