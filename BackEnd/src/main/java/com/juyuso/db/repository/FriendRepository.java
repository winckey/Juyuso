package com.juyuso.db.repository;

import com.juyuso.db.entity.Friend;

import com.juyuso.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Transactional(readOnly = true)
    Optional<Friend> findByFromAndTo(User from, User to);

    @Transactional(readOnly = true)
    Optional<Friend> findByFromAndToId(User from, long toId);

    @Modifying(clearAutomatically = true)
    void deleteByFromIdAndToId(long from, long toId);
}