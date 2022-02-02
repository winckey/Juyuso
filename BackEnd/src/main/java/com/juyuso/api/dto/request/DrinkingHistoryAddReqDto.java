package com.juyuso.api.dto.request;

import com.juyuso.db.entity.DrinkingCategory;
import com.juyuso.db.entity.DrinkingHistory;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
@ApiModel("DrinkingHistory Add Req Dto")
public class DrinkingHistoryAddReqDto {

    private Long soju;
    private Long beer;
    private String date;




    public DrinkingHistory toEntity(User user, DrinkingCategory drinkingCategory) {
        if(drinkingCategory.getName().equals("소주")) {
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

