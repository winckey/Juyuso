package com.juyuso.api.service.meeting;

import com.juyuso.api.dto.request.meeting.MeetingCreateReqDto;
import com.juyuso.api.dto.request.meeting.MeetingModifyReqDto;
import com.juyuso.db.entity.meeting.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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
   void plusCnt(Long meetingId);
   void minusCnt(Long meetingId);


}
