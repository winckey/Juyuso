package com.juyuso.db.repository.drinking;

import com.juyuso.db.entity.drinking.DrinkingCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkingCategoryRepository extends JpaRepository<DrinkingCategory, Long> {


    Optional<DrinkingCategory> findByName(String name);
}
