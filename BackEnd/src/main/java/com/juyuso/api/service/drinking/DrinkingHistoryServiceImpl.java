package com.juyuso.api.service.drinking;

import com.juyuso.api.dto.request.DrinkingHistoryAddReqDto;
import com.juyuso.db.entity.drinking.DrinkingCategory;
import com.juyuso.db.entity.drinking.DrinkingHistory;
import com.juyuso.db.entity.user.User;
import com.juyuso.db.repository.drinking.DrinkingCategoryRepository;
import com.juyuso.db.repository.drinking.DrinkingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DrinkingHistoryServiceImpl implements DrinkingHistoryService{

    private final String soju = "소주";
    private final String beer = "맥주";

    private final DrinkingHistoryRepository drinkingHistoryRepository;
    private final DrinkingCategoryRepository drinkingCategoryRepository;

    @Autowired
    public DrinkingHistoryServiceImpl (DrinkingHistoryRepository drinkingHistoryRepository,
                                  DrinkingCategoryRepository drinkingCategoryRepository) {
        this.drinkingHistoryRepository = drinkingHistoryRepository;
        this.drinkingCategoryRepository = drinkingCategoryRepository;
    }

    @Override
    public List<DrinkingHistory> findAll(User user) {
        Long userId = user.getId();
        return drinkingHistoryRepository.findByDateGroup(userId);
    }
    @Override
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

    @Override
    @Transactional
    public void addLocalDrinkingHistory(DrinkingHistoryAddReqDto reqDto, User user) {
        DrinkingCategory drinkingCategory;
        if(reqDto.getSoju() != 0) {
            drinkingCategory = drinkingCategoryRepository.findByName(soju).get();
            drinkingHistoryRepository.save(reqDto.toEntity(user, drinkingCategory, reqDto.getDate()));
        }
        if(reqDto.getBeer() != 0) {
            drinkingCategory = drinkingCategoryRepository.findByName(beer).get();
            drinkingHistoryRepository.save(reqDto.toEntity(user, drinkingCategory, reqDto.getDate()));
        }
    }
}
