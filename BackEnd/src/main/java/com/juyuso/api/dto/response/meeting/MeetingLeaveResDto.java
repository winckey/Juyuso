package com.juyuso.api.dto.response.meeting;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingLeaveResDto {
    @ApiModelProperty(name = "미팅방 번호", example = "1")
    private Long meetingId;

    public static MeetingLeaveResDto of (Long meetingId) {
        MeetingLeaveResDto res = new MeetingLeaveResDto();
        res.setMeetingId(meetingId);
        return res;
    }

}
