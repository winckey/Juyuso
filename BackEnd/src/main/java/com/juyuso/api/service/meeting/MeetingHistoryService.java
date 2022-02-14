package com.juyuso.api.service.meeting;

import com.juyuso.db.entity.meeting.MeetingHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeetingHistoryService {

    Long saveMeetingHistory(Long meetingId, String userId, String action);
    List<MeetingHistory> getMeetingHistoryList(String userId);

}
