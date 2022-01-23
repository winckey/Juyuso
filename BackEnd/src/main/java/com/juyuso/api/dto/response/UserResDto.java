package com.juyuso.api.dto.response;

import com.juyuso.api.dto.UserInfoDto;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("User")
public class UserResDto extends BaseResponseBody {

    private UserInfoDto user;

    public static UserResDto of(Integer statusCode, String message, User user) {
        UserResDto res = new UserResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUser(UserInfoDto.of(user));
        return res;
    }
}
