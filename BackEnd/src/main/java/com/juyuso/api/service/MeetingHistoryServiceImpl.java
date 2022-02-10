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
public class MeetingHistoryServiceImpl implements MeetingHistoryService{

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final MeetingHistoryRepository meetingHistoryRepository;

    @Autowired
    public MeetingHistoryServiceImpl (MeetingRepository meetingRepository, UserRepository userRepository,
                                 MeetingHistoryRepository meetingHistoryRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.meetingHistoryRepository = meetingHistoryRepository;
    }


    @Override
    public Long saveMeetingHistory(Long meetingId, String userId, String action) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        MeetingHistory meetingHistory;
        if(action.equals("생성")) {
            meetingHistory = new MeetingHistory(meeting, user, "생성");
        } else if(action.equals("입장")) {
            meetingHistory = new MeetingHistory(meeting, user, "입장");
        } else {
            meetingHistory = new MeetingHistory(meeting, user, "퇴장");
        }
        return meetingHistoryRepository.save(meetingHistory).getId();
    }

    @Override
    public List<MeetingHistory> getMeetingHistoryList(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return  meetingHistoryRepository.findByUser(user);
    }

}
