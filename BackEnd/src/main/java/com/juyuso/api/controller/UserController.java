package com.juyuso.api.controller;

import com.juyuso.api.dto.request.LoginReqDto;
import com.juyuso.api.dto.request.RegisterReqDto;
import com.juyuso.api.dto.response.LoginResDto;
import com.juyuso.api.service.UserService;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.common.util.JwtTokenUtil;
import com.juyuso.db.entity.Users;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestBody @ApiParam(value="회원가입 정보<br>birthDate 칼럼은 yyyy-MM-dd 포맷<br>gender 칼럼은 M 또는 F 값으로 전달", required = true) RegisterReqDto registerInfo) {

        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        System.out.println("JOIN");
        System.out.println(registerInfo);
        Users user = userService.createUser(registerInfo);
        System.out.println(user);
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
    public ResponseEntity<LoginResDto> login(@RequestBody @ApiParam(value="로그인 정보", required = true) LoginReqDto loginInfo) {
        String userId = loginInfo.getId();
        String password = loginInfo.getPassword();

        Users user = userService.getUserByUserId(userId);
        // 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
        if (passwordEncoder.matches(password, user.getPassword())) {
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            String token = JwtTokenUtil.getToken(userId);
            System.out.println("Token : " + token);
            return ResponseEntity.ok(LoginResDto.of(200, "Success", token));
        }
        // 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
        return ResponseEntity.status(401).body(LoginResDto.of(401, "Invalid Password", null));
    }
}
