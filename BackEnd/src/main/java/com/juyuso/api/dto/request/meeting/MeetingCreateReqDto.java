package com.juyuso.api.dto.request.meeting;

import com.juyuso.db.entity.meeting.Meeting;
import com.juyuso.db.entity.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MeetingCreateReqDto {

    @NotBlank
    @ApiModelProperty(name = "방 이름", example = "프로젝트 째고 술마실분")
    private String meetingName;

    @ApiModelProperty(name = "방 비밀번호", example = "12345")
    private String meetingPassword;

    @NotNull
    @ApiModelProperty(name = "공개방 설정", example = "true")
    private boolean common;

    @NotNull
    @ApiModelProperty(name = "방 활성화  방폭파시 false", example = "true")
    private boolean active;

    @ApiModelProperty(name = "해시태그", example = "부산 20 친구")
    private List<String> hashTag;

    @NotBlank
    @ApiModelProperty(name = "방 배경화면", example = "포장마차1")
    private String img;

    @NotBlank
    @ApiModelProperty(name = "인원수", example = "2")
    private int cnt;

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
                active, common, img, 1);
    }


}
