package com.juyuso.api.service;

import com.juyuso.api.dto.request.DrinkingHistoryAddReqDto;
import com.juyuso.db.entity.DrinkingCategory;
import com.juyuso.db.entity.DrinkingHistory;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.DrinkingCategoryRepository;
import com.juyuso.db.repository.DrinkingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

public interface DrinkingHistoryService {

    List<DrinkingHistory> findAll(User user);
    void addDrinking (DrinkingHistoryAddReqDto reqDto, User user);
    void addLocalDrinkingHistory(DrinkingHistoryAddReqDto reqDto, User user);

}
