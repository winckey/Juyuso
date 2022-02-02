package com.juyuso.api.service;

import com.juyuso.api.dto.request.MeetingCreateReqDto;
import com.juyuso.api.dto.response.MeetingListResDto;
import com.juyuso.db.entity.HashTag;
import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.HashTagRepository;
import com.juyuso.db.repository.MeetingRepository;
import com.juyuso.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public Long createMeeting(MeetingCreateReqDto dto, String username) {
        User user = userRepository.findByUserId(username).get();
        Meeting meeting = dto.toEntity(user);
        List<String> list = dto.getHashTag();

        for(int i = 0; i < list.size(); i++) {
                hashTagRepository.save(new HashTag(meeting, list.get(i)));
        }
        return meetingRepository.save(meeting).getId();
    }

    public Page<Meeting> findAllByTag(String tags, Pageable pageable) {
        return meetingRepository.findListByHashTag(tags, pageable);
    }

    public Page<Meeting> findAllByTitle(String title, Pageable pageable) {
        return meetingRepository.findAllByTitleContaining(title, pageable);
    }

    public Page<Meeting> findAll(Pageable pageable) {
        return meetingRepository.findAll(pageable);
    }
    public Meeting findByMeetingId(Long meetingId) {
        return meetingRepository.findById(meetingId).get();
    }

    @Transactional
    public Long changeActiveMeetingByMeetingId(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).get();
        meeting.changeActive();
        return meetingId;
    }



}
