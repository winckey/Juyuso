package com.juyuso.api.dto.response;

import com.juyuso.api.dto.UserInfoDto;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("FriendResponse")
public class FriendResDto extends BaseResponseBody {

    private UserInfoDto friend;
    private List<UserInfoDto> friendList;

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

    public void setFriendList(List<User> user) {
        friendList = new ArrayList<>();
        for (int i =0 ; i < user.size() ; i ++){
            friendList.add(UserInfoDto.of(user.get(i)));
        }
    }


    public void setFriend(User request) {
        friend= UserInfoDto.of(request);
    }
}
