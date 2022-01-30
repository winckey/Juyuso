package com.juyuso.api.dto.response;

import com.juyuso.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserImgPostResDto extends BaseResponseBody {

    private String imgUrl;

    public static UserImgPostResDto of(Integer statusCode, String message, String imgUrl) {
        UserImgPostResDto res = new UserImgPostResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setImgUrl(imgUrl);
        return res;
    }
}
