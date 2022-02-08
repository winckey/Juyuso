package com.juyuso.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class MeetingModifyReqDto {

    @ApiModelProperty(name ="테마" ,example = "테마1")
    private String url;
}
