package com.juyuso.api.service;

import com.juyuso.api.dto.request.MeetingCreateReqDto;
import com.juyuso.api.dto.request.MeetingModifyReqDto;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.db.entity.HashTag;
import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.User;
import com.juyuso.db.repository.HashTagRepository;
import com.juyuso.db.repository.MeetingRepository;
import com.juyuso.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.transaction.Transactional;
import java.util.List;


public interface MeetingService {

   Long createMeeting(MeetingCreateReqDto dto, String username);
   Page<Meeting> findAllByTags(String tags, Pageable pageable);
   Page<Meeting> findAllByTagContainingAndCommon(String tags, Pageable pageable, Boolean common);
   Page<Meeting> findAllByTitleContainingAndCommon(String title, Pageable pageable, Boolean common);
   Page<Meeting> findAllByTitleContaining(String title, Pageable pageable);
   Page<Meeting> findAll(Pageable pageable);
   Page<Meeting> findAllByCommon(Pageable pageable, Boolean common);
   Meeting findByMeetingId(Long meetingId);
   Long changeActiveMeetingByMeetingId(Long meetingId);
   Meeting modifyMeeting(Long meetingId, MeetingModifyReqDto reqDto);


}
