package com.juyuso.api.dto.response;

import com.juyuso.db.entity.HashTag;
import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
public class MeetingListResDto {

    private Long meetingId;
    private String meetingTitle;
    private List<String> hashtag = new ArrayList<>();
    private boolean common;

    public MeetingListResDto(Long meetingId, String meetingTitle,
                             List<String> hashtag, boolean common) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.hashtag = hashtag;
        this.common = common;
    }
    public MeetingListResDto() {

    }

    public static Page<MeetingListResDto> of (Page<Meeting> list) {
        Page<MeetingListResDto> dtoPage = list.map(new Function<Meeting, MeetingListResDto>() {
            @Override
            public MeetingListResDto apply(Meeting entity) {
                MeetingListResDto dto = new MeetingListResDto();
                dto.setMeetingId(entity.getId());
                dto.setMeetingTitle(entity.getTitle());
                dto.setHashtag(Meeting.toList(entity));
                dto.setCommon(entity.isCommon());
                return dto;
            }
        });
        return dtoPage;
    }


}
