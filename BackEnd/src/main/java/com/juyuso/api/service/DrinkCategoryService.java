package com.juyuso.api.service;

import com.juyuso.db.entity.DrinkingCategory;
import com.juyuso.db.repository.DrinkingCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface DrinkCategoryService{
    DrinkingCategory findByName(String name);
}
