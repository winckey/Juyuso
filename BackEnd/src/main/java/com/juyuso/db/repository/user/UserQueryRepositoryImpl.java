package com.juyuso.db.repository.user;


import com.juyuso.db.entity.friend.QFriend;
import com.juyuso.db.entity.user.QUser;
import com.juyuso.db.entity.user.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryRepositoryImpl implements UserQueryRepository {
    private final JPAQueryFactory query;

    private QUser qUser = new QUser("user1");
    private QFriend qFriend = new QFriend("friend1");

    @Override
//    @Query(value = "select u from User u where u.nickname like %:nickname% and u.id not in (select f.to.id from Friend f where f.from = :user)")
    public List<User> findNotFriendListByNicknameAndUser(String  nickname,  User user) {



        return (List<User>) query
                .select(qUser)
                .from(qUser)
                .where(qUser.nickname.contains(nickname)
                        .and(qUser.id.notIn(
                                JPAExpressions
                                        .select(qFriend.to.id)
                                        .from(qFriend)
                                        .where(qFriend.from.eq(user)))))
                .fetch();
    }

    private BooleanExpression userEmailEq(String userEmail) {
        return userEmail != null ? qUser.email.eq(userEmail) : null;
    }
}
