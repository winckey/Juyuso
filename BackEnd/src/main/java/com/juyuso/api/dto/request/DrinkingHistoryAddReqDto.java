package com.juyuso.api.dto.request;

import com.juyuso.db.entity.drinking.DrinkingCategory;
import com.juyuso.db.entity.drinking.DrinkingHistory;
import com.juyuso.db.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
public class DrinkingHistoryAddReqDto {

    @ApiModelProperty(name = "마신 소주 양(잔)", example = "123")
    private Long soju;
    @ApiModelProperty(name = "마신 맥주 양(캔)", example = "456")
    private Long beer;
    @ApiModelProperty(name = "날짜", example = "2022-02-22")
    private String date;


    public DrinkingHistory toEntity(User user, DrinkingCategory drinkingCategory) {
        if(drinkingCategory.getId() == 1) {
            return new DrinkingHistory(user, LocalDateTime.now(), drinkingCategory, this.soju);
        }
        else {
            return new DrinkingHistory(user, LocalDateTime.now(), drinkingCategory, this.beer);
        }
    }

    public DrinkingHistory toEntity(User user, DrinkingCategory drinkingCategory, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date toDate = null;
        try {
            toDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDateTime localDateTime = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if(drinkingCategory.getId() == 1) {
            return new DrinkingHistory(user, localDateTime, drinkingCategory, this.soju);
        }
        else {
            return new DrinkingHistory(user, localDateTime, drinkingCategory, this.beer);
        }

    }

}

