package com.juyuso.api.dto.response.meeting;

import com.juyuso.db.entity.meeting.Meeting;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MeetingModifyResDto {

    @ApiModelProperty(name = "미팅방 아이디", example = "12")
    private Long meetingId;
    @ApiModelProperty(name = "방 이름", example = "술먹자 ~")
    private String meetingTitle;
    @ApiModelProperty(name = "테마", example = "테마1")
    private String url;



    public static MeetingModifyResDto of(Meeting meeting) {
        MeetingModifyResDto res = new MeetingModifyResDto();
        res.setMeetingId(meeting.getId());
        res.setMeetingTitle(meeting.getTitle());
        res.setUrl(meeting.getUrl());
        return res;
    }
}
