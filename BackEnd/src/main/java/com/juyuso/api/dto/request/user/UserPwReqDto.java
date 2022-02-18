package com.juyuso.api.dto.request.user;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserPwReqDto {

    @NotBlank
    private String password;
}
