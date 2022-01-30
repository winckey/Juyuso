package com.juyuso.api.service;

import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.api.dto.request.UserModifyReqDto;
import com.juyuso.api.dto.request.UserPwCheckReqDto;
import com.juyuso.db.entity.User;
import com.juyuso.db.entity.UserImg;
import com.juyuso.db.repository.RegionRepository;
import com.juyuso.db.repository.UserImgRepository;
import com.juyuso.db.repository.UserRepository;
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
    public User createUser(RegisterReqDto registerRequestDto) {
        User userEntity = registerRequestDto.toEntity();
        userEntity.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        userEntity.setRegion(regionRepository.getById(registerRequestDto.getRegionId()));
        return userRepository.save(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByUserId(String userId) {
        // Optional 용법 체크 필요 (warning 참조)
        return userRepository.findByUserId(userId).get();
    }

    @Override
    public Boolean checkDuplicateUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Override
    public User modifyUser(User user, UserModifyReqDto userModifyReqDto) {
        user.setPassword(passwordEncoder.encode(userModifyReqDto.getPassword()));
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
            userImgRepository.deleteByUserId(user.getId());
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

        userImgRepository.save(userImg);

        return savingFileName;
    }

    @Override
    public Boolean checkPw(User userDetails, UserPwCheckReqDto userPwCheckReqDto) {
        return passwordEncoder.matches(userPwCheckReqDto.getPassword(), userDetails.getPassword());
    }
}
