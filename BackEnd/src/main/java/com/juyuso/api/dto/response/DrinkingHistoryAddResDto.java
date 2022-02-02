package com.juyuso.api.dto.response;

import com.juyuso.api.dto.request.DrinkingHistoryAddReqDto;
import com.juyuso.db.entity.DrinkingHistory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DrinkingHistoryAddResDto {

    private Long soju;
    private Long beer;
    private String date;
    private String userId;

    public static DrinkingHistoryAddResDto of (String userId, DrinkingHistoryAddReqDto reqDto) {
        DrinkingHistoryAddResDto res = new DrinkingHistoryAddResDto();
        res.setUserId(userId);
        res.setBeer(reqDto.getBeer());
        res.setSoju(reqDto.getSoju());
        res.setDate(reqDto.getDate());
        return res;
    }
}
