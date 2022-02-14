package com.juyuso.api.dto.response;

import com.juyuso.api.dto.UserInfoDto;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserLoginResponse")
public class LoginResDto extends BaseResponseBody {

    @ApiModelProperty(name="JWT 인증 토큰", example="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN...")
    private String accessToken;

    private UserInfoDto user;

    public static LoginResDto of(Integer statusCode, String message, String accessToken, User user) {
        LoginResDto res = new LoginResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        res.setUser(UserInfoDto.of(user));
        return res;
    }

    public static LoginResDto of(Integer statusCode, String message, String accessToken) {
        LoginResDto res = new LoginResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setAccessToken(accessToken);
        return res;
    }
}
