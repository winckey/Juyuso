package com.juyuso.db.repository;

import com.juyuso.db.entity.Ban;
import com.juyuso.db.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;


public interface BanRepository extends JpaRepository<Ban, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete FROM ban b " +
                    " WHERE ban_user_id = :from and user_id = :to", nativeQuery = true)
    void deleteByBothUserId(Long from, Long to);


}
