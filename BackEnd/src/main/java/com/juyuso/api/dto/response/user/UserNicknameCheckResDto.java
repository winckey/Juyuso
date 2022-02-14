package com.juyuso.api.dto.response.user;

import com.juyuso.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNicknameCheckResDto extends BaseResponseBody {

    @ApiModelProperty(name = "요청한 닉네임")
    private String nickname;

    @ApiModelProperty(name = "중복 여부")
    private Boolean duplicate;

    public static UserNicknameCheckResDto of(Integer statusCode, String message, String nickname, Boolean duplicate) {
        UserNicknameCheckResDto res = new UserNicknameCheckResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setNickname(nickname);
        res.setDuplicate(duplicate);
        return res;
    }
}
