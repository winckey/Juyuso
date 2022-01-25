package com.juyuso.api.dto.response;

import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("BanResponse")
public class BanResDto extends BaseResponseBody {

    private User user;

    public static BanResDto of(Integer statusCode, String message) {
        BanResDto res = new BanResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        return res;
    }
}
