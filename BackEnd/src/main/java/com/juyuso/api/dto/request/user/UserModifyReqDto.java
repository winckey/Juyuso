package com.juyuso.api.dto.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class UserModifyReqDto {

    @Email
    @ApiModelProperty(name = "유저 Email", example = "user@naver.com")
    private String email;

    @NotBlank
    @ApiModelProperty(name = "닉네임", example = "냠냠")
    private String nickname;

    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 010-ABCD-ABCD")
    @ApiModelProperty(name = "전화번호", example = "010-1234-5678")
    private String phone;

    @NotNull
    @Min(1) @Max(8)
    @ApiModelProperty(name = "지역 코드")
    private Long regionId;

    @ApiModelProperty(name = "자기 소개")
    private String description;

}