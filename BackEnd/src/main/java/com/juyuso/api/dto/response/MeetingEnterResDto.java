package com.juyuso.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingEnterResDto {
    @ApiModelProperty(name = "미팅 방 번호", example = "2")
    private Long meetingId;

    @ApiModelProperty(name = "인원수", example = "3")
    private int cnt;

    public static MeetingEnterResDto of(Long meetingId, int cnt) {
        MeetingEnterResDto res = new MeetingEnterResDto();
        res.setMeetingId(meetingId);
        res.setCnt(cnt);

        return res;
    }
}
