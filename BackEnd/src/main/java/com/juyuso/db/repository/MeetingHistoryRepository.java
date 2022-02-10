package com.juyuso.db.repository;
import com.juyuso.db.entity.MeetingHistory;
import com.juyuso.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingHistoryRepository extends JpaRepository<MeetingHistory, Long> {

    List<MeetingHistory> findByUser (User user);
}
