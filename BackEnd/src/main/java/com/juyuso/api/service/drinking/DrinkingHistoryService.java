package com.juyuso.api.service.drinking;

import com.juyuso.api.dto.request.DrinkingHistoryAddReqDto;
import com.juyuso.db.entity.drinking.DrinkingHistory;
import com.juyuso.db.entity.user.User;

import java.util.List;

public interface DrinkingHistoryService {

    List<DrinkingHistory> findAll(User user);
    void addDrinking (DrinkingHistoryAddReqDto reqDto, User user);
    void addLocalDrinkingHistory(DrinkingHistoryAddReqDto reqDto, User user);

}
