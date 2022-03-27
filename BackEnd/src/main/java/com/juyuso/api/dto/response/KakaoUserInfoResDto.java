package com.juyuso.api.dto.response;

import com.juyuso.api.dto.KakaoProfile;
import com.juyuso.common.model.response.BaseResponseBody;
import lombok.Data;

@Data
public class KakaoUserInfoResDto extends BaseResponseBody {

    private boolean join;
    private UserInfo info;

    @Data
    private static class UserInfo {
        private String id;
        private String nickname;
        private String email;
        private Character gender;
    }

    public static KakaoUserInfoResDto of(Integer statusCode, String message, boolean join, KakaoProfile kakaoProfile) {
        KakaoUserInfoResDto res = new KakaoUserInfoResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setJoin(join);
        UserInfo userInfo = new UserInfo();
        userInfo.setId("kakao_" + String.valueOf(kakaoProfile.getId()));
        if (!join) {
            KakaoProfile.Properties kakaoProfileProperties = kakaoProfile.getProperties();
            if (kakaoProfileProperties != null) {
                String nickname = kakaoProfileProperties.getNickname();
                if (nickname != null) userInfo.setNickname(nickname);
            }
            String email = kakaoProfile.getKakao_account().getEmail();
            if (email != null) userInfo.setEmail(email);
            String gender = kakaoProfile.getKakao_account().getGender();
            if (gender != null) {
                if ("male".equals(gender)) userInfo.setGender('M');
                else if ("female".equals(gender)) userInfo.setGender('F');
            }
        }
        res.setInfo(userInfo);
        return res;
    }
}
