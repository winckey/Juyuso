package com.juyuso.db.repository.user;



import com.juyuso.db.entity.user.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserQueryRepository {
    public List<User> findNotFriendListByNicknameAndUser(String nickname,  User user);

}
