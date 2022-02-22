package com.juyuso.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@AllArgsConstructor
public class FriendReqDto {

    @NotNull
    @ApiModelProperty(name = "유저/신청 ID", example = "id (pk 고유번호넘겨 주세요)")
    private Long id;

}
