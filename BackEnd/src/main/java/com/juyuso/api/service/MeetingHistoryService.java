package com.juyuso.api.service;

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
public class MeetingHistoryService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final MeetingHistoryRepository meetingHistoryRepository;

    @Autowired
    public MeetingHistoryService(MeetingRepository meetingRepository, UserRepository userRepository,
                                 MeetingHistoryRepository meetingHistoryRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.meetingHistoryRepository = meetingHistoryRepository;
    }


    public Long saveMeetingHistory(Long meetingId, String userId, String action) {
        Meeting meeting = meetingRepository.findById(meetingId).get();
        User user = userRepository.findByUserId(userId).get();
        MeetingHistory meetingHistory;
        if(action.equals("생성")) {
            meetingHistory = new MeetingHistory(meeting, user, "생성");
        } else if(action.equals("입장")) {
            meetingHistory = new MeetingHistory(meeting, user, "입장");
        } else {
            meetingHistory = new MeetingHistory(meeting, user, "퇴장");
        }
        Long meetingHistoryId = meetingHistoryRepository.save(meetingHistory).getId();
        return meetingHistoryId;
    }




    public List<MeetingHistory> getMeetingHistoryList(String userId) {
        User user = userRepository.findByUserId(userId).get();
        List<MeetingHistory> list = meetingHistoryRepository.findByUser(user);

        return list;
    }



}
