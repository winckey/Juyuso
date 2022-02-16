package com.juyuso.api.dto.response.meeting;

import com.juyuso.db.entity.meeting.Meeting;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MeetingResDto {
    @ApiModelProperty(name = "방 번호", example = "12")
    private Long meetingId;
    @ApiModelProperty(name = "방 이름", example = "술먹자 ")
    private String meetingTitle;
    @ApiModelProperty(name = "해시태그 ", example = "부산 , 20, 40")
    private List<String> hashtag = new ArrayList<>();
    @ApiModelProperty(name = "공개방 여부", example = "true")
    private boolean common;
    @ApiModelProperty(name = "유저 닉네임", example = "부산박보검")
    private String nickName;
    @ApiModelProperty(name = "방 비밀번호", example = "1323")
    private String meetingPassword;
    @ApiModelProperty(name = "유저 프로필 사진", example = "5df8d943-66ef-4c00-8481-6f41a32a09e3.PNG")
    private String userImg;
    @ApiModelProperty(name = "방 테마", example = "포창마차 테마")
    private String theme;
    @ApiModelProperty(name = "방 인원수", example = "2")
    private int cnt;

    public static MeetingResDto of (Meeting meeting) {
        MeetingResDto res = new MeetingResDto();
        res.setMeetingId(meeting.getId());
        res.setMeetingTitle(meeting.getTitle());
        res.setHashtag(Meeting.toList(meeting));
        res.setCommon(meeting.isCommon());
        res.setNickName(meeting.getOwner().getNickname());
        res.setMeetingPassword(meeting.getPassword());
        if(meeting.getOwner().getUserImg() != null) {
            res.setUserImg(meeting.getOwner().getUserImg().getFileUrl());
        }
        res.setTheme(meeting.getUrl());
        res.setCnt(meeting.getCnt());

        return res;
    }
}
