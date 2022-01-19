package com.juyuso.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@ApiModel("UserLoginRequest")
public class LoginReqDto {
    @ApiModelProperty(name = "유저 ID", example = "your_id")
    private String id;

    @ApiModelProperty(name = "유저 Password", example = "your_password")
//    @NotNull
    private String password;
}
