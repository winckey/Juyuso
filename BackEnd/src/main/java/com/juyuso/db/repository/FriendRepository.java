package com.juyuso.db.repository;

import com.juyuso.db.entity.Friend;

import com.juyuso.db.entity.FriendRequest;
import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query(value = "SELECT u.* FROM User u , Friend f " +
            "WHERE f.from_id = %:id% and u.id = f.from_id", nativeQuery = true)
    List<User> findListByUserId(@Param("id")String id);

    @Query(value = "(SELECT * " +
            "FROM  friend f " +
            " WHERE f.from_id = :from" +
            " and f.user_id = :to)", nativeQuery = true)
    Optional<Friend> findFriendByFromToId(@Param("from")long from  , @Param("to")long to);


    @Modifying
    @Query(value = "delete FROM friend f " +
                    "WHERE from_id = :from and user_id = :to", nativeQuery = true)
    void deleteBothByUserId(long from , long to );
}
