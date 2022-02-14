package com.juyuso.api.dto.response.meeting;

import com.juyuso.db.entity.meeting.Meeting;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
public class MeetingListResDto {

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

    public MeetingListResDto() {

    }

    public MeetingListResDto(Long meetingId, String meetingTitle,
                             List<String> hashtag, boolean common) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.hashtag = hashtag;
        this.common = common;
    }

    public MeetingListResDto(Long meetingId, String meetingTitle, List<String> hashtag, boolean common,
                             String nickName, String meetingPassword, String userImg, String theme) {
        this.meetingId = meetingId;
        this.meetingTitle = meetingTitle;
        this.hashtag = hashtag;
        this.common = common;
        this.nickName = nickName;
        this.meetingPassword = meetingPassword;
        this.userImg = userImg;
        this.theme = theme;
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
                dto.setNickName(entity.getOwner().getNickname());
                dto.setMeetingPassword(entity.getPassword());
                dto.setTheme(entity.getUrl());
                if(entity.getOwner().getUserImg() != null) {
                    dto.setUserImg(entity.getOwner().getUserImg().getFileUrl());
                }
                dto.setCnt(entity.getCnt());
                return dto;
            }
        });
        return dtoPage;
    }


}
