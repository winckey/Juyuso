package com.juyuso.api.service.drinking;

import com.juyuso.db.entity.drinking.DrinkingCategory;
import com.juyuso.db.repository.drinking.DrinkingCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkCategoryServiceImpl implements DrinkCategoryService{

    private final DrinkingCategoryRepository drinkingCategoryRepository;

    @Autowired
    public DrinkCategoryServiceImpl(DrinkingCategoryRepository drinkingCategoryRepository) {
        this.drinkingCategoryRepository = drinkingCategoryRepository;
    }


    @Override
    public DrinkingCategory findByName(String name) {
        DrinkingCategory drinkingCategory = drinkingCategoryRepository.findByName(name).get();
        return drinkingCategory;
    }
}
