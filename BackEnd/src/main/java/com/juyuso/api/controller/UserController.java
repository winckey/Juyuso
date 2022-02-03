package com.juyuso.api.controller;

import com.juyuso.api.dto.request.UserLoginReqDto;
import com.juyuso.api.dto.request.UserRegisterReqDto;
import com.juyuso.api.dto.request.UserModifyReqDto;
import com.juyuso.api.dto.request.UserPwCheckReqDto;
import com.juyuso.api.dto.response.*;
import com.juyuso.api.service.UserService;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.common.util.JwtTokenUtil;
import com.juyuso.db.entity.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Slf4j
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping()
    @ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @Valid @RequestBody @ApiParam(value="회원가입 정보<br>birthDate 칼럼은 yyyy-MM-dd 포맷<br>gender 칼럼은 M 또는 F 값으로 전달", required = true) UserRegisterReqDto registerInfo) {
        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        log.info("SIGN UP", registerInfo);
        User user = userService.createUser(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = LoginResDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<LoginResDto> login(@RequestBody @ApiParam(value="로그인 정보", required = true) UserLoginReqDto loginInfo) {
        String userId = loginInfo.getId();
        String password = loginInfo.getPassword();

        User user = userService.getUserByUserId(userId);
        // 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
        if (passwordEncoder.matches(password, user.getPassword())) {
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            String token = JwtTokenUtil.getToken(userId);
            return ResponseEntity.ok(LoginResDto.of(200, "Success", token, user));
        }
        // 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
        return ResponseEntity.status(401).body(LoginResDto.of(401, "Invalid Password", null));
    }

    @GetMapping("/id/{userId}")
    @ApiOperation(value = "아이디 중복 체크", notes = "아이디 중복 여부를 알려준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserIdCheckResDto.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<UserIdCheckResDto> checkDuplicateUserId(@PathVariable String userId) {

        Boolean result = userService.checkDuplicateUserId(userId);

        return ResponseEntity.status(200).body(UserIdCheckResDto.of(200, "Success", userId, result));
    }

    @GetMapping("/info")
    @ApiOperation(value = "회원 본인 정보 조회", notes = "로그인한 회원 본인의 정보를 응답한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserResDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<UserResDto> getUserInfo(@ApiIgnore Authentication authentication) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        User userDetails = (User) authentication.getDetails();

        return ResponseEntity.status(200).body(UserResDto.of(200, "Success", userDetails));
    }

    @PutMapping("/info")
    @ApiOperation(value = "회원 본인 정보 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserResDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<UserResDto> modifyUser(@ApiIgnore Authentication authentication, @RequestBody @ApiParam(value="수정 정보", required = true) UserModifyReqDto userModifyReqDto) {
        User userDetails = (User) authentication.getDetails();

        User result = userService.modifyUser(userDetails, userModifyReqDto);

        return ResponseEntity.status(200).body(UserResDto.of(200, "Success", result));
    }

    @PostMapping(value = "/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "프로필 이미지 업로드")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserImgPostResDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<UserImgPostResDto> insertImg(@ApiIgnore Authentication authentication, @RequestPart("img") MultipartFile img) {
        User userDetails = (User) authentication.getDetails();

        String imgUrl = userService.saveImg(userDetails, img);

        return ResponseEntity.status(200).body(UserImgPostResDto.of(200, "Success", imgUrl));
    }

    @PostMapping(value = "/pw/check")
    @ApiOperation(value = "패스워드 체크", notes = "로그인된 계정의 패스워드 일치 여부를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserPwCheckResDto.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<UserPwCheckResDto> checkPw(@ApiIgnore Authentication authentication, @Valid @RequestBody UserPwCheckReqDto userPwCheckReqDto) {
        User userDetails = (User) authentication.getDetails();

        Boolean valid = userService.checkPw(userDetails, userPwCheckReqDto);

        return ResponseEntity.status(200).body(UserPwCheckResDto.of(200, "Success", valid));
    }
}
