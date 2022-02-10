package com.juyuso.api.dto.request;

import com.juyuso.common.validation.Date;
import com.juyuso.db.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
public class UserRegisterReqDto {

    @NotBlank
    @ApiModelProperty(name="유저 ID", example="your_id")
    private String id;

    @NotBlank
    @ApiModelProperty(name="유저 Password", example="your_password")
    private String password;

    @Email
    @ApiModelProperty(name="유저 Email", example="user@naver.com")
    private String email;

    @NotBlank
    @ApiModelProperty(name="닉네임", example="냠냠")
    private String nickname;

    @Date
    @ApiModelProperty(name="생년월일", example="1999-08-09")
    private String birthDate;

    @NotNull
    @ApiModelProperty(name="성별", example="M")
    private Character gender;

    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 010-ABCD-ABCD")
    @ApiModelProperty(name="전화번호", example="010-1234-5678")
    private String phone;

    @NotNull
    @Min(1) @Max(8)
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