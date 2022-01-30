package com.juyuso.api.dto.response;

import com.juyuso.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPwCheckResDto extends BaseResponseBody {

    private Boolean valid;

    public static UserPwCheckResDto of(Integer statusCode, String message, Boolean valid) {
        UserPwCheckResDto res = new UserPwCheckResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setValid(valid);
        return res;
    }
}
