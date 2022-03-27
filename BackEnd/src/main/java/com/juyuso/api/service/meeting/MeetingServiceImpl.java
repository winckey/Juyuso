package com.juyuso.api.service.meeting;

import com.juyuso.api.dto.request.meeting.MeetingCreateReqDto;
import com.juyuso.api.dto.request.meeting.MeetingModifyReqDto;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.db.entity.HashTag;
import com.juyuso.db.entity.meeting.Meeting;
import com.juyuso.db.entity.user.User;
import com.juyuso.db.repository.HashTagRepository;
import com.juyuso.db.repository.meeting.MeetingRepository;
import com.juyuso.db.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService{

    private final MeetingRepository meetingRepository;
    private final UserRepository userRepository;
    private final HashTagRepository hashTagRepository;

    @Autowired
    public MeetingServiceImpl (MeetingRepository meetingRepository, UserRepository userRepository,
                           HashTagRepository hashTagRepository ) {
        this.meetingRepository = meetingRepository;
        this.userRepository = userRepository;
        this.hashTagRepository = hashTagRepository;
    }

    @Transactional
    public Long createMeeting(MeetingCreateReqDto dto, String username) {
        User user = userRepository.findByUserId(username).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Meeting meeting = dto.toEntity(user);
        List<String> list = dto.getHashTag();
        for(int i = 0; i < list.size(); i++) {
            hashTagRepository.save(new HashTag(meeting, list.get(i)));
        }
        return meetingRepository.save(meeting).getId();
    }

    public Page<Meeting> findAllByTags(String tags, Pageable pageable) {
        return meetingRepository.findAllByTags(tags, pageable);
    }

    public Page<Meeting> findAllByTagContainingAndCommon(String tags, Pageable pageable, Boolean common) {
        return meetingRepository.findAllByTagContainingAndCommon(tags, pageable, common);
    }

    public Page<Meeting> findAllByTitleContainingAndCommon(String title, Pageable pageable, Boolean common) {
        return meetingRepository.findAllByTitleContainingAndCommon(title, pageable, common);
    }

    public Page<Meeting> findAllByTitleContaining(String title, Pageable pageable) {
        return meetingRepository.findAllByTitleContaining(title, pageable);
    }

    public Page<Meeting> findAll(Pageable pageable) {
        return meetingRepository.findAll(pageable);
    }

    public Page<Meeting> findAllByCommon(Pageable pageable, Boolean common) {
        return meetingRepository.findAllByCommon(pageable, common);
    }

    public Meeting findByMeetingId(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));
        return meeting;
    }

    @Transactional
    public Long changeActiveMeetingByMeetingId(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));
        meeting.changeActive();
        return meetingId;
    }

    @Transactional
    public Meeting modifyMeeting(Long meetingId, MeetingModifyReqDto reqDto) {
        Meeting meeting = meetingRepository.findById(meetingId).orElseThrow(() -> new CustomException(ErrorCode.MEETING_NOT_FOUND));
        meeting.modifyUrl(reqDto.getUrl());
        return meeting;

    }
    @Transactional
    public void plusCnt(Long meetingId) {
        meetingRepository.plusCnt(meetingId);
    }

    @Transactional
    public void minusCnt(Long meetingId) {
        meetingRepository.minusCnt(meetingId);
    }


    @PreDestroy
    public void changeActiveMeeting() {
        System.out.println("종료직전 모든 방 active false 로 바꾸기 !! ");
//        meetingRepository.changeActiveMeeting();
    }


}
