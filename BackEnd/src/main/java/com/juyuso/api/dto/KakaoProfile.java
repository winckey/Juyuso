package com.juyuso.api.dto;

import lombok.*;

@Data
public class KakaoProfile {
    private Long id;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Data
    public static class KakaoAccount {
        private String name;
        private String email;
        private String birthyear;
        private String birthday;
        private String gender;
        private String phone_number;
    }

    @Data
    public static class Properties {
        private String nickname;
    }
}