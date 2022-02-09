package com.juyuso.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageReqDto {
    private String chatRoomId;
    private String writerId;
    private String message;
    private String receiverId;
}
