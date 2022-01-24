package com.juyuso.api.dto.request;

import com.juyuso.api.dto.response.MeetingCreateResDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingLeaveReqDto {
    @ApiModelProperty(name = "미팅방 번호", example = "1")
    private Long meetingId;
    @ApiModelProperty(name = "남은 인원수", example = "3")
    private int cnt;

    public static MeetingLeaveReqDto of (Long meetingId, int cnt) {
        MeetingLeaveReqDto res = new MeetingLeaveReqDto();
        res.setMeetingId(meetingId);
        res.setCnt(cnt);

        return res;
    }

}
