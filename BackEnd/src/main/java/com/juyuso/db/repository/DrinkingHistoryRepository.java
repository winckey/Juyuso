package com.juyuso.db.repository;

import com.juyuso.db.entity.DrinkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkingHistoryRepository extends JpaRepository<DrinkingHistory, Long> {


    @Query(value = "SELECT SUM(quantity) as quantity, ANY_VALUE(drinking_history_id) as drinking_history_id," +
            " ANY_VALUE(drinking_category_id) as drinking_category_id, " +
            "ANY_VALUE(drinking_time) as drinking_time, ANY_VALUE(user_id) as user_id " +
            "FROM drinking_history dh WHERE user_id = :userId " +
            "GROUP BY DATE(dh.drinking_time)" ,nativeQuery = true)
    List<DrinkingHistory> findByDateGroup(@Param("userId")Long userId);




}
