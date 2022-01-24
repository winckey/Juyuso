package com.juyuso.db.repository;

import com.juyuso.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user " +
                    "where nickname LIKE %:keyword%", nativeQuery = true)
    List<User> findALLByNickname(@Param("keyword")String keyword);

}
