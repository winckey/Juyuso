package com.juyuso.db.repository;

import com.juyuso.db.entity.Friend;
import com.juyuso.db.entity.FriendRequest;
import com.juyuso.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {



    @Query(value = "(SELECT * " +
            "FROM  friend_request f " +
            " WHERE f.from_id = :from" +
            " and f.to_id = :to)", nativeQuery = true)
   FriendRequest findRequestByfromId(@Param("from")long from  , @Param("to")long to);

}
