package com.juyuso.api.dto.response;

import com.juyuso.api.dto.request.DrinkingHistoryAddReqDto;
import com.juyuso.db.entity.DrinkingHistory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("DrinkingHistory Add Response Dto")
public class DrinkingHistoryAddResDto {

    @ApiModelProperty(name = "마신 소주 양(잔)", example = "123")
    private Long soju;
    @ApiModelProperty(name = "마신 맥주 양(캔)", example = "456")
    private Long beer;
    @ApiModelProperty(name = "날짜", example = "2022-02-22")
    private String date;
    @ApiModelProperty(name = "유저 아이디", example = "yyyyy")
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
