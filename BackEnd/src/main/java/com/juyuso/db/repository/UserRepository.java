package com.juyuso.db.repository;

import com.juyuso.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);

    @Query(value = "SELECT * FROM user " +
            "where nickname LIKE %:keyword%", nativeQuery = true)
    List<User> findALLByNickname(@Param("keyword")String keyword);


    @Query(value = "select * from user " +
                   " u where id in " +
                        "(SELECT f.user_id " +
                        "FROM  Friend f " +
                        " WHERE f.from_id = :id)", nativeQuery = true)
    List<User> findListByUserId(@Param("id")String id);


    @Query(value = "select * from user " +
            " u where id in " +
            "(SELECT f.from_id " +
            "FROM  Friend_request f " +
            " WHERE f.user_id = :id)", nativeQuery = true)
    List<User> findRequestListByUserId(@Param("id")String id);

}
