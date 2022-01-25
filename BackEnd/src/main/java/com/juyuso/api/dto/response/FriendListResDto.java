package com.juyuso.api.dto.response;

import com.juyuso.api.dto.UserInfoDto;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.Friend;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("FriendResponse")
public class FriendListResDto extends BaseResponseBody {


    private List<UserInfoDto> friendList;
    private List<UserInfoDto> friendRequestList;


    public static FriendListResDto of(Integer statusCode, String message, List<User> user ,  List<User> request) {
        FriendListResDto res = new FriendListResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setFriendList(user);

        res.setFriendRequestList(request);
        return res;
    }

    public void setFriendList(List<User> user) {
        friendList = new ArrayList<>();
        for (int i =0 ; i < user.size() ; i ++){
            friendList.add(UserInfoDto.of(user.get(i)));
        }
    }


    public void setFriendRequestList(List<User> request) {
        friendRequestList = new ArrayList<>();
        for (int i =0 ; i < request.size() ; i ++){
            friendRequestList.add(UserInfoDto.of(request.get(i)));
        }
    }


}
