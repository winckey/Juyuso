package com.juyuso.api.dto.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("Meeting Create Response")
public class MeetingCreateResDto {

    @ApiModelProperty(name = "방 번호", example = "1")
    private Long meetingId;

    @ApiModelProperty(name = "방 이름" , example = "술한잔 하실분")
    private String meetingName;
    @ApiModelProperty(name = "방 비밀번호" , example = "12345")
    private String password;
    @ApiModelProperty(name = "유저아이디" , example = "ssafy1234")
    private String userId;

    public MeetingCreateResDto(String meetingName, String password, String userId) {
        this.meetingName = meetingName;
        this.password = password;
        this.userId = userId;
    }
    public MeetingCreateResDto() {

    }

    public static MeetingCreateResDto of(Long meetingId, String meetingName, String password, String userId) {

        MeetingCreateResDto res = new MeetingCreateResDto();
        res.setMeetingId(meetingId);
        res.setMeetingName(meetingName);
        res.setPassword(password);
        res.setUserId(userId);
        return res;
    }



}
