package com.juyuso.api.dto.response.drinking;

import com.juyuso.db.entity.drinking.DrinkingHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DrinkingHistoryListResDto {

    @ApiModelProperty(name = "날짜 ", example = "2020-02-12")
    private String date;
    @ApiModelProperty(name = "술먹은 양", example = "43")
    private Long count;

    public static List<DrinkingHistoryListResDto> of (List<DrinkingHistory> historyList) {
        List<DrinkingHistoryListResDto> list = new ArrayList<>();
        for(DrinkingHistory drinkingHistory: historyList) {
            DrinkingHistoryListResDto res = new DrinkingHistoryListResDto();
            res.setDate(drinkingHistory.getDrinkingTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            res.setCount(drinkingHistory.getQuantity());
            list.add(res);
        }
        return list;
    }


}
