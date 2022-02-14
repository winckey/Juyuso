package com.juyuso.api.dto.response.friend;

import com.juyuso.api.dto.UserInfoDto;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("FriendSearchListResDto")
public class FriendSearchListResDto extends BaseResponseBody {


    private List<UserInfoDto> friendList;
    private List<UserInfoDto> notFriendList;


    public static FriendSearchListResDto of(Integer statusCode, String message, List<User> user , List<User> not) {
        FriendSearchListResDto res = new FriendSearchListResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setFriendList(user);

        res.setNotFriendList(not);
        return res;
    }


    public void setFriendList(List<User> user) {
        friendList = new ArrayList<>();
        for (int i =0 ; i < user.size() ; i ++){
            friendList.add(UserInfoDto.of(user.get(i)));
        }
    }


    public void setNotFriendList(List<User> not) {
        notFriendList = new ArrayList<>();
        for (int i =0 ; i < not.size() ; i ++){
            notFriendList.add(UserInfoDto.of(not.get(i)));
        }
    }


}
