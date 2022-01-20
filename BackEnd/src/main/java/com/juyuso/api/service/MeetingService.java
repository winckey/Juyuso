package com.juyuso.api.service;

import com.juyuso.api.dto.request.MeetingCreateReqDto;
import com.juyuso.db.entity.HashTag;
import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.HashTagRepository;
import com.juyuso.db.repository.MeetingRepository;
import com.juyuso.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;

    @Autowired
    public MeetingService (MeetingRepository meetingRepository, UserRepository userRepository, HashTagRepository hashTagRepository) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.hashTagRepository = hashTagRepository;

    }

    @Transactional
    public void createMeeting(MeetingCreateReqDto dto, String username) {
        User user = userRepository.findByUserId(username).get();
        Meeting meeting = dto.toEntity(user);
        List<String> list = dto.getHashTag();

        for(int i = 0; i < list.size(); i++) {
                hashTagRepository.save(new HashTag(meeting, list.get(i)));
        }
        meetingRepository.save(meeting);
    }

}
