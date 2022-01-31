package com.juyuso.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
    Exception 처리
 */
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseEntity> handleNotFoundUser(UserNotFoundException ex){
        ErrorResponseEntity response = new ErrorResponseEntity(ErrorCode.USER_NOT_FOUND , ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseEntity> handleAll(Exception ex){
        ErrorResponseEntity response = new ErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR , ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FriendException.class)
    public ResponseEntity<ErrorResponseEntity> handleFriend(FriendException ex){
        ErrorResponseEntity response = new ErrorResponseEntity(ErrorCode.FRIEND_LOGIC_ERROR , ex.getMessage());//커스텀할 에러 메세지 상수로 정의하고
                                                                                                                //서비스 단에서 가져온 메세지 저장
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
