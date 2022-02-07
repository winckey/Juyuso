package com.juyuso.api.service;

import com.juyuso.api.dto.request.UserRegisterReqDto;
import com.juyuso.api.dto.request.UserModifyReqDto;
import com.juyuso.api.dto.request.UserPwReqDto;
import com.juyuso.db.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User createUser(UserRegisterReqDto userRegisterInfo);
    User getUserByUserId(String userId);
    Boolean checkDuplicateUserId(String userId);
    User modifyUser(User user, UserModifyReqDto userModifyReqDto);
    void modifyPw(User user, UserPwReqDto userPwReqDto);
    String saveImg(User user, MultipartFile multipartFile);
    void saveFcmToken(User user, String fcmToken);
    Boolean checkPw(User userDetails, UserPwReqDto userPwReqDto);
}