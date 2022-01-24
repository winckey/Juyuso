package com.juyuso.api.dto.response;

import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.Friend;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("FriendResponse")
public class FriendListResDto extends BaseResponseBody {


    private List<Friend> friendList;

    public static FriendListResDto of(Integer statusCode, String message, List<Friend> list) {
        FriendListResDto res = new FriendListResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setFriendList(list);
        return res;
    }



}
