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

@Service
public class DrinkingHistoryService {

    private final String soju = "소주";
    private final String beer = "맥주";

    private final DrinkingHistoryRepository drinkingHistoryRepository;
    private final DrinkingCategoryRepository drinkingCategoryRepository;

    @Autowired
    public DrinkingHistoryService(DrinkingHistoryRepository drinkingHistoryRepository,
                                  DrinkingCategoryRepository drinkingCategoryRepository) {
        this.drinkingHistoryRepository = drinkingHistoryRepository;
        this.drinkingCategoryRepository = drinkingCategoryRepository;
    }

    public List<DrinkingHistory> findAll(User user) {
        Long userId = user.getId();
        return drinkingHistoryRepository.findByDateGroup(userId);
    }
    @Transactional
    public void addDrinking (DrinkingHistoryAddReqDto reqDto, User user) {
        DrinkingCategory drinkingCategory;
        if(reqDto.getSoju() != 0) {
            drinkingCategory = drinkingCategoryRepository.findByName(soju).get();
            drinkingHistoryRepository.save(reqDto.toEntity(user, drinkingCategory));
        }
        if(reqDto.getBeer() != 0) {
            drinkingCategory = drinkingCategoryRepository.findByName(beer).get();
            drinkingHistoryRepository.save(reqDto.toEntity(user, drinkingCategory));
        }
    }


}
