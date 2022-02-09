package com.juyuso.api.dto;

import com.juyuso.db.entity.Region;
import com.juyuso.db.entity.User;
import com.juyuso.db.entity.UserImg;
import com.juyuso.db.entity.chat.Message;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageinfoDto {

    private Long id;
    private Long userId;
    private String message;
    private Long roomId;


    public static MessageinfoDto of(Message message) {
        MessageinfoDto messageinfoDto = new MessageinfoDto();
        messageinfoDto.setId(message.getId());
        messageinfoDto.setMessage(message.getMessage());
        messageinfoDto.setUserId(message.getUser().getId());
        messageinfoDto.setRoomId(message.getChatRoom().getId());
        return messageinfoDto;
    }
}
