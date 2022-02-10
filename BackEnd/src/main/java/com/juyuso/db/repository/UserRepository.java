package com.juyuso.db.repository;

import com.juyuso.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);

    List<User> findByNicknameContaining(String nickname);

    @Query(value = "select f.to from Friend f where f.from = :user and f.to.nickname like %:nickname%")
    List<User> findFriendListByNicknameAndUser(@Param("nickname") String nickname, @Param("user") User user);

    @Query(value = "select u from User u where u.nickname like %:nickname% and u.id not in (select f.to.id from Friend f where f.from = :user)")
    List<User> findNotFriendListByNicknameAndUser(@Param("nickname") String nickname, @Param("user") User user);

    @Query(value = "select f.to from Friend f where f.from = :user")
    List<User> findFriendListByUser(@Param("user") User user);

    @Query(value = "select f.fromUser from FriendRequest f where f.toUser = :user")
    List<User> findFriendRequestListByUser(@Param("user") User user);

    @Query(value = "select b.banUser from FriendBan b where b.user = :user")
    List<User> findFriendBanListByUser(@Param("user") User user);
}