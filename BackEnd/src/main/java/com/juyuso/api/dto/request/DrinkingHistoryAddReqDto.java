package com.juyuso.api.dto.request;

import com.juyuso.db.entity.DrinkingCategory;
import com.juyuso.db.entity.DrinkingHistory;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@ApiModel("DrinkingHistory Add Req Dto")
public class DrinkingHistoryAddReqDto {

    private Long soju;
    private Long beer;


    public DrinkingHistory toEntity(User user, DrinkingCategory drinking) {
        if(drinking.getName().equals("소주")) {
            return new DrinkingHistory(user, LocalDateTime.now(), drinking, this.soju);
        }
        else {
            return new DrinkingHistory(user, LocalDateTime.now(), drinking, this.beer);
        }

    }

}

