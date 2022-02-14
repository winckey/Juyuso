package com.juyuso.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class UserLoginReqDto {

    @NotBlank
    @ApiModelProperty(name = "유저 ID", example = "your_id")
    private String id;

    @ApiModelProperty(name = "유저 Password", example = "your_password")
    private String password;

    @NotBlank
    @ApiModelProperty(name = "FCM Token")
    private String fcmToken;
}
