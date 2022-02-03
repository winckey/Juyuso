package com.juyuso.api.service;

import com.juyuso.api.dto.request.UserRegisterReqDto;
import com.juyuso.api.dto.request.UserModifyReqDto;
import com.juyuso.api.dto.request.UserPwCheckReqDto;
import com.juyuso.db.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User createUser(UserRegisterReqDto userRegisterInfo);
    User getUserByUserId(String userId);
    Boolean checkDuplicateUserId(String userId);
    User modifyUser(User user, UserModifyReqDto userModifyReqDto);
    String saveImg(User user, MultipartFile multipartFile);
    Boolean checkPw(User userDetails, UserPwCheckReqDto userPwCheckReqDto);
}