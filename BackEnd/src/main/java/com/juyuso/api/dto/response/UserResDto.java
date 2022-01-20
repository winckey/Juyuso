package com.juyuso.api.dto.response;

import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("UserDto")
public class UserResDto extends BaseResponseBody {

    private User user;

    public static UserResDto of(Integer statusCode, String message, User user) {
        UserResDto res = new UserResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUser(user);
        return res;
    }
}
