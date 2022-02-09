package com.juyuso.api.dto.response;

import com.juyuso.api.dto.MessageinfoDto;
import com.juyuso.api.dto.UserInfoDto;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.User;
import com.juyuso.db.entity.chat.Message;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("User")
public class ChatMessageResDto extends BaseResponseBody {

    private List<MessageinfoDto> messageList;

    public static ChatMessageResDto of(Integer statusCode, String message, List<Message> messageList) {
        ChatMessageResDto res = new ChatMessageResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setMessageList(messageList);
        return res;
    }


    public void setMessageList(List<Message> messages) {
        messageList = new ArrayList<>();
        for (int i =0 ; i < messages.size() ; i ++){
            messageList.add(MessageinfoDto.of(messages.get(i)));
        }
    }
}
