package com.juyuso.api.service.user;

import com.juyuso.api.dto.request.user.UserRegisterReqDto;
import com.juyuso.api.dto.request.user.UserModifyReqDto;
import com.juyuso.api.dto.request.user.UserPwReqDto;
import com.juyuso.db.entity.user.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User createUser(UserRegisterReqDto userRegisterInfo);
    User getUserByUserId(String userId);
    Boolean checkDuplicateUserId(String userId);
    Boolean checkDuplicateNickname(String nickname);
    Boolean checkDuplicateEmail(String email);
    User modifyUser(User user, UserModifyReqDto userModifyReqDto);
    void modifyPw(User user, UserPwReqDto userPwReqDto);
    String saveImg(User user, MultipartFile multipartFile);
    void saveFcmToken(User user, String fcmToken);
    Boolean checkPw(User userDetails, UserPwReqDto userPwReqDto);
}