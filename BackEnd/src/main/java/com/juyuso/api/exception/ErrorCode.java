package com.juyuso.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    USER_PW_INVALID(UNAUTHORIZED, "사용자의 PW가 일치하지 않습니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(NOT_FOUND, "해당 유저의 정보를 찾을 수 없습니다."),
    FRIEND_REQUEST_NOT_FOUND(NOT_FOUND, "친구 요청 정보를 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    USER_ID_DUPLICATE(CONFLICT, "중복된 사용자 ID입니다."),
    FRIEND_REQUEST_DUPLICATE(CONFLICT, "이미 친구 요청하였습니다."),
    FRIEND_DUPLICATE(CONFLICT, "이미 등록된 친구입니다.");

    private HttpStatus httpStatus;
    private String message;
}
