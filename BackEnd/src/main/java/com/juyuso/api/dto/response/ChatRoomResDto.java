package com.juyuso.api.dto.response;

import com.juyuso.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("User")
public class ChatRoomResDto extends BaseResponseBody {

    private Long roomId;

    public static ChatRoomResDto of(Integer statusCode, String message, Long roomId) {
        ChatRoomResDto res = new ChatRoomResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setRoomId(roomId);
        return res;
    }
}
