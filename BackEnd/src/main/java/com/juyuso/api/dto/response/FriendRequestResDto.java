package com.juyuso.api.dto.response;

import com.juyuso.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("FriendResponse")
public class FriendRequestResDto extends BaseResponseBody {



    public static FriendRequestResDto of(Integer statusCode, String message) {
        FriendRequestResDto res = new FriendRequestResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        return res;
    }



}
