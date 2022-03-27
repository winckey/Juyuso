package com.juyuso.api.service.drinking;

import com.juyuso.db.entity.drinking.DrinkingCategory;

public interface DrinkCategoryService{
    DrinkingCategory findByName(String name);
}
