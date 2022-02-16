package com.juyuso.db.repository.user;

import com.juyuso.db.entity.user.User;
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

    boolean existsByEmail(String email);

    @Query(value = "select f.to from Friend f where f.from = :user")
    List<User> findFriendListByUser(@Param("user") User user);

    @Query(value = "select f.fromUser from FriendRequest f where f.toUser = :user")
    List<User> findFriendRequestListByUser(@Param("user") User user);

    @Query(value = "select b.banUser from FriendBan b where b.user = :user")
    List<User> findFriendBanListByUser(@Param("user") User user);
}