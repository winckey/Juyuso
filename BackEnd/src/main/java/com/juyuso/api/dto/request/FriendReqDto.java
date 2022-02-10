package com.juyuso.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class FriendReqDto {

    @NotBlank
    @ApiModelProperty(name = "유저/신청 ID", example = "id (pk 고유번호넘겨 주세요)")
    private Long id;

}
