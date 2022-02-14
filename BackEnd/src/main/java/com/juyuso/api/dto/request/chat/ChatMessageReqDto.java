package com.juyuso.api.dto.request.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageReqDto {
    @NotBlank
    private String chatRoomId;
    @NotBlank
    private String writerId;
    @NotBlank
    private String writerName;
    @NotBlank
    private String message;
    @NotBlank
    private String receiverId;
}
