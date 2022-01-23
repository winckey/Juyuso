package com.juyuso.api.service;

import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.api.dto.request.UserModifyReqDto;
import com.juyuso.db.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User createUser(RegisterReqDto userRegisterInfo);
    User getUserByUserId(String userId);
    Boolean checkDuplicateUserId(String userId);
    User modifyUser(User user, UserModifyReqDto userModifyReqDto);
    void saveImg(User user, MultipartFile multipartFile);
}