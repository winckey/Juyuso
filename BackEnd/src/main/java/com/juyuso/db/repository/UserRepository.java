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


    @Query(value = "SELECT * FROM (SELECT * from user" +
            " where id in " +
            "(SELECT f.user_id" +
            " from friend f " +
            "WHERE f.from_id = :id)) as u " +
            "where u.nickname LIKE %:keyword%", nativeQuery = true)
    List<User> findFriendByNickname(@Param("keyword")String keyword , @Param("id")Long id);


    @Query(value = "SELECT * FROM (SELECT * from user" +
            " where id not in " +
            "(SELECT f.user_id" +
            " from friend f " +
            "WHERE f.from_id = :id)) as u " +
            "where u.nickname LIKE %:keyword%", nativeQuery = true)
    List<User> findNotFriendByNickname(@Param("keyword")String keyword , @Param("id")Long id);

    @Query(value = "select * from user " +
            " u where id in " +
            "(SELECT f.user_id " +
            "FROM  friend f " +
            " WHERE f.from_id = :id)", nativeQuery = true)
    List<User> findListByUserId(@Param("id")Long id);


    @Query(value = "select * from user " +
            " u where id in " +
            "(SELECT f.from_id " +
            "FROM  friend_request f " +
            " WHERE f.to_id = :id)", nativeQuery = true)
    List<User> findRequestListByUserId(@Param("id")Long id);


    @Query(value = "select * from user " +
            " u where id in " +
            "(SELECT b.ban_user_id " +
            "FROM  ban b " +
            " WHERE b.user_id = :id)", nativeQuery = true)
    List<User> findBanListByUserId(Long id);
}
