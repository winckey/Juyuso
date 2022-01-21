package com.juyuso.api.dto.request;

import com.juyuso.db.entity.Region;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
@ApiModel("UserRegisterRequest")
public class RegisterReqDto {
    @ApiModelProperty(name="유저 ID", example="your_id")
    private String id;

    @ApiModelProperty(name="유저 Password", example="your_password")
//    @NotNull
    private String password;

    @ApiModelProperty(name="유저 Email", example="user@naver.com")
//    @Email
    private String email;

    @ApiModelProperty(name="닉네임", example="냠냠")
    private String nickname;

    @ApiModelProperty(name="생년월일", example="1999-08-09")
    private String birthDate;

    @ApiModelProperty(name="성별", example="M")
    private Character gender;

    @ApiModelProperty(name="전화번호", example="010-1234-5678")
    private String phone;

    @ApiModelProperty(name="지역 코드")
    private Long regionId;

    public User toEntity() {
        return User.builder()
                .userId(id)
                .password(password)
                .nickname(nickname)
                .email(email)
                .birthDate(LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .regDate(LocalDateTime.now())
                .gender(gender)
                .phone(phone)
                .build();
    }
}