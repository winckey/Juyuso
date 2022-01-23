package com.juyuso.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserModifyReqDto {

    @ApiModelProperty(name = "유저 Password", example = "your_password")
    private String password;

    @ApiModelProperty(name = "유저 Email", example = "user@naver.com")
    private String email;

    @ApiModelProperty(name = "닉네임", example = "냠냠")
    private String nickname;

    @ApiModelProperty(name = "전화번호", example = "010-1234-5678")
    private String phone;

    @ApiModelProperty(name = "지역 코드")
    private Long regionId;

    @ApiModelProperty(name = "자기 소개")
    private String description;

}