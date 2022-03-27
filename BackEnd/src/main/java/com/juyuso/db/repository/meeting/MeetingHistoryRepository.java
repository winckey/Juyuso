package com.juyuso.db.repository.meeting;
import com.juyuso.db.entity.meeting.MeetingHistory;
import com.juyuso.db.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingHistoryRepository extends JpaRepository<MeetingHistory, Long> {

    List<MeetingHistory> findByUser (User user);
}
