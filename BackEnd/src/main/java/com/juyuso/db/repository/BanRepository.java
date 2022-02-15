package com.juyuso.db.repository;

import com.juyuso.db.entity.friend.FriendBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;


public interface BanRepository extends JpaRepository<FriendBan, Long> {
    
    void deleteByUserIdAndBanUserId(Long from, Long to);
}
