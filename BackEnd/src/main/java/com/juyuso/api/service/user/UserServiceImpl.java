package com.juyuso.api.service.user;

import com.juyuso.api.dto.request.user.UserRegisterReqDto;
import com.juyuso.api.dto.request.user.UserModifyReqDto;
import com.juyuso.api.dto.request.user.UserPwReqDto;
import com.juyuso.api.exception.CustomException;
import com.juyuso.api.exception.ErrorCode;
import com.juyuso.db.entity.user.User;
import com.juyuso.db.entity.user.UserImg;
import com.juyuso.db.repository.RegionRepository;
import com.juyuso.db.repository.user.UserImgRepository;
import com.juyuso.db.repository.user.UserRepository;
import org.apache.commons.io.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserImgRepository userImgRepository;
    private final RegionRepository regionRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.fileUpload.path}")
    private String uploadPath;

    public UserServiceImpl(UserRepository userRepository, UserImgRepository userImgRepository, RegionRepository regionRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userImgRepository = userImgRepository;
        this.regionRepository = regionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(UserRegisterReqDto registerRequestDto) {
        if (checkDuplicateUserId(registerRequestDto.getId())) throw new CustomException(ErrorCode.USER_ID_DUPLICATE);
        if (checkDuplicateNickname(registerRequestDto.getNickname())) throw new CustomException(ErrorCode.USER_NICKNAME_DUPLICATE);
        if (checkDuplicateEmail(registerRequestDto.getEmail())) throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATE);

        final String provider = registerRequestDto.getProvider();
        User userEntity = registerRequestDto.toEntity();
        if (provider == null || provider.isEmpty()) userEntity.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        userEntity.setRegion(regionRepository.getById(registerRequestDto.getRegionId()));
        if (provider != null && !provider.isEmpty()) userEntity.setProvider(provider);
        return userRepository.save(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkDuplicateUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkDuplicateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User modifyUser(User user, UserModifyReqDto userModifyReqDto) {
        user.setPhone(userModifyReqDto.getPhone());
        user.setEmail(userModifyReqDto.getEmail());
        if (user.getRegion().getId() != userModifyReqDto.getRegionId())
            user.setRegion(regionRepository.findById(userModifyReqDto.getRegionId()).get());
        user.setDescription(userModifyReqDto.getDescription());
        user.setNickname(userModifyReqDto.getNickname());
        userRepository.save(user);

        return user;
    }

    @Override
    public String saveImg(User user, MultipartFile multipartFile) {
        // find file upload directory
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        // if the img file already exists, then delete file
        UserImg img = user.getUserImg();
        if (img != null) {
            String fileUrl = img.getFileUrl();
            File file = new File(uploadPath + File.separator, fileUrl);
            if (file.exists()) file.delete();
            userImgRepository.deleteById(user.getUserImg().getId());
        }

        String fileName = multipartFile.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String extension = FilenameUtils.getExtension(fileName);
        String savingFileName = uuid + "." + extension;
        File destFile = new File(uploadPath + File.separator + savingFileName);

        try {
            multipartFile.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserImg userImg = UserImg.builder()
                .user(user)
                .fileContentType(multipartFile.getContentType())
                .fileName(fileName)
                .fileSize(multipartFile.getSize())
                .fileUrl(savingFileName)
                .build();


        user.chageUserImg(userImg);
        userRepository.save(user);
//        userImgRepository.save(userImg);

        return savingFileName;
    }

    @Override
    public void saveFcmToken(User user, String fcmToken) {
        user.setFcmToken(fcmToken);
        userRepository.save(user);
    }

    @Override
    public void modifyPw(User user, UserPwReqDto userPwReqDto) {
        user.setPassword(passwordEncoder.encode(userPwReqDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkPw(User userDetails, UserPwReqDto userPwReqDto) {
        if (passwordEncoder.matches(userPwReqDto.getPassword(), userDetails.getPassword())) return true;
        else throw new CustomException(ErrorCode.USER_PW_INVALID);
    }

    @Override
    public void removeFcmToken(User user) {
        user.setFcmToken(null);
        userRepository.save(user);
    }
}
