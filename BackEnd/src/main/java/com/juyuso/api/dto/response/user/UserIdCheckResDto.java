package com.juyuso.api.dto.response.user;

import com.juyuso.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserIdCheckResDto extends BaseResponseBody {

    @ApiModelProperty(name = "요청한 ID")
    private String userId;

    @ApiModelProperty(name = "중복 여부")
    private Boolean duplicate;

    public static UserIdCheckResDto of(Integer statusCode, String message, String userId, Boolean duplicate) {
        UserIdCheckResDto res = new UserIdCheckResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUserId(userId);
        res.setDuplicate(duplicate);
        return res;
    }
}
