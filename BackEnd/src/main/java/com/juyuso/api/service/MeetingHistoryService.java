package com.juyuso.api.service;

import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.MeetingHistory;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.MeetingHistoryRepository;
import com.juyuso.db.repository.MeetingRepository;
import com.juyuso.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeetingHistoryService {

    Long saveMeetingHistory(Long meetingId, String userId, String action);
    List<MeetingHistory> getMeetingHistoryList(String userId);

}
