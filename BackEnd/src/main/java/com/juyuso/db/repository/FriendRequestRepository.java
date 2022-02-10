package com.juyuso.db.repository;

import com.juyuso.db.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Query(value = "select f from FriendRequest f where f.fromUser.id = :from and f.toUser.id = :to")
    Optional<FriendRequest> findById(@Param("from") long from, @Param("to") long to);

}
