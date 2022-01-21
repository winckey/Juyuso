package com.juyuso.api.dto.response;

import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("FriendResponse")
public class FriendResDto extends BaseResponseBody {

    private User friend;
    private List<User> friendList;

    public static FriendResDto of(Integer statusCode, String message, User user) {
        FriendResDto res = new FriendResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setFriend(user);
        return res;
    }


    public static FriendResDto of(Integer statusCode, String message, List<User> userList) {
        FriendResDto res = new FriendResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setFriendList(userList);
        return res;
    }


}
