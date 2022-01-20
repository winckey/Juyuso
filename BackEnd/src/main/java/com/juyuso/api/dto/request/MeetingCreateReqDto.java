package com.juyuso.api.dto.request;

import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ApiModel("Create Meeting")
public class MeetingCreateReqDto {

    private String meetingName;
    private String meetingPassword;
    private boolean common;
    private boolean active;
    private List<String> hashTag;
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
                active, common);
    }


}
