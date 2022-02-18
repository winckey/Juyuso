package com.juyuso.api.dto;

import com.juyuso.db.entity.Region;
import com.juyuso.db.entity.user.User;
import com.juyuso.db.entity.user.UserImg;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoDto {

    private Long id;
    private String userId;
    private String nickname;
    private String description;
    private String phone;
    private String email;
    private Boolean img;
    private String imgUrl;
    private Long age;
    private LocalDate birthDate;
    private LocalDateTime regDate;
    private Character gender;
    private Region region;
    private String provider;

    public static UserInfoDto of(User user) {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(user.getId());
        userInfoDto.setUserId(user.getUserId());
        userInfoDto.setNickname(user.getNickname());
        userInfoDto.setDescription(user.getDescription());
        userInfoDto.setPhone(user.getPhone());
        userInfoDto.setEmail(user.getEmail());
        userInfoDto.setBirthDate(user.getBirthDate());
        userInfoDto.setRegDate(user.getRegDate());
        userInfoDto.setGender(user.getGender());
        userInfoDto.setRegion(user.getRegion());

        UserImg userImg = user.getUserImg();
        if (userImg != null) {
            userInfoDto.setImg(true);
            userInfoDto.setImgUrl(userImg.getFileUrl());
        } else {
            userInfoDto.setImg(false);
            userInfoDto.setImgUrl("default_profile_img.png");
        }

        String provider = user.getProvider();
        if (provider != null && !provider.isEmpty()) userInfoDto.setProvider(provider);

        userInfoDto.setAge(user.getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS));

        return userInfoDto;
    }
}
