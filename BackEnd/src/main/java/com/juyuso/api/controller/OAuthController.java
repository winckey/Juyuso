package com.juyuso.api.controller;

import com.juyuso.api.dto.KakaoProfile;
import com.juyuso.api.dto.response.KakaoUserInfoResDto;
import com.juyuso.api.dto.OAuthKakaoToken;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.api.service.KakaoService;
import com.juyuso.api.service.UserService;
import com.juyuso.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "OAuth Kakao")
@RestController
@RequestMapping("api/oauth/kakao")
@RequiredArgsConstructor
public class OAuthController {

    private final KakaoService kakaoService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<? extends BaseResponseBody> redirect(
            @ApiParam(value = "인가 코드", required = true)
            @RequestParam String code
    ) {
        OAuthKakaoToken tokenResDto = kakaoService.getAccessToken(code);
        String accessToken = tokenResDto.getAccess_token();
        log.info(accessToken);

        KakaoProfile kakaoProfile = kakaoService.getKakaoProfile(accessToken);
        log.info(kakaoProfile.toString());

        // 이미 가입된 유저라면 로그인 진행
        if (userService.checkDuplicateUserId("kakao_" + String.valueOf(kakaoProfile.getId()))) {
            return ResponseEntity.ok(KakaoUserInfoResDto.of(200, "카카오로 가입된 계정이 존재합니다. 로그인을 시도하세요.", true, kakaoProfile));
        }

        // 가입되지 않은 유저라면?
        // 이메일 중복 체크
        String email = kakaoProfile.getKakao_account().getEmail();
        if (userService.checkDuplicateEmail(email)) {
            throw new CustomException(ErrorCode.OAUTH_EMAIL_DUPLICATE);
        }

        // 불러온 회원 정보 반환
        return ResponseEntity.ok(KakaoUserInfoResDto.of(200, "Success", false, kakaoProfile));
    }
}
