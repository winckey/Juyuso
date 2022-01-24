package com.juyuso.api.dto.request;

import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ApiModel("Create Meeting")
public class MeetingCreateReqDto {

    @ApiModelProperty(name = "방 이름", example = "프로젝트 째고 술마실분")
    private String meetingName;
    @ApiModelProperty(name = "방 비밀번호", example = "12345")
    private String meetingPassword;
    @ApiModelProperty(name = "공개방 설정", example = "true")
    private boolean common;
    @ApiModelProperty(name = "방 활성화  방폭파시 false", example = "true")
    private boolean active;
    @ApiModelProperty(name = "해시태그", example = "부산 20 친구")
    private List<String> hashTag;
    @ApiModelProperty(name = "방 배경화면", example = "포장마차1")
    private String img;

    MeetingCreateReqDto() {

    }

    public MeetingCreateReqDto(String meetingName, String meetingPassword, boolean common,
                               boolean isActive, List<String> hashTag, String img) {
        this.meetingName = meetingName;
        this.meetingPassword = meetingPassword;
        this.common = common;
        this.active = isActive;
        this.hashTag = hashTag;
        this.img = img;
    }

    public Meeting toEntity(User user) {
        return new Meeting(user, meetingName, meetingPassword,
                LocalDateTime.now(), LocalDateTime.now(),
                active, common, img);
    }


}
