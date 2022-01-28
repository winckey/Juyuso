package com.juyuso.db.repository;

import com.juyuso.db.entity.DrinkingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkingCategoryRepository extends JpaRepository<DrinkingCategory, Long> {


    Optional<DrinkingCategory> findByName(String name);
}
