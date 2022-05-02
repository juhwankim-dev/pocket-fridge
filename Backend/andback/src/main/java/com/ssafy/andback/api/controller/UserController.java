package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.dto.request.LoginRequestDto;
import com.ssafy.andback.api.dto.response.SingleResponseDto;
import com.ssafy.andback.config.jwt.JwtAuthenticationProvider;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.UserRepository;
import io.swagger.annotations.*;
import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * UserController
 * 유저와 관련된 api를 모은 Controller
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-04-25
 **/

@RequiredArgsConstructor
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "회원가입", notes = "유저 정보를 받아 DB에 저장한다.")
    @PostMapping
    public ResponseEntity<BaseResponseDto> signUp(@Valid UserDto userDto, @ApiIgnore Errors errors) {
        String result = userService.insertUser(userDto);
        if (errors.hasErrors() || result.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "회원가입 실패"));
        }

        return ResponseEntity.ok(BaseResponseDto.of(200, "회원가입 성공"));
    }

    @ApiOperation(value = "이메일 중복 검사", notes = "유저 이메일 중복 검사")
    @GetMapping("/checkemail/{userEmail}")
    public ResponseEntity<BaseResponseDto> checkUserEmail(@PathVariable String userEmail) {
        String result = userService.checkUserEmail(userEmail);
        if (result.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "이메일 중복"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "이메일 사용 가능"));
    }

    @ApiOperation(value = "닉네임 중복 검사", notes = "유저 닉네임 중복 검사")
    @GetMapping("/checknickname/{userNickname}")
    public ResponseEntity<BaseResponseDto> checkUserNickname(@PathVariable String userNickname) {
        String result = userService.checkUserNickname(userNickname);
        if (result.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "닉네임 중복"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "닉네임 사용 가능"));
    }

    @ApiOperation(value = "이메일 인증번호 전송", notes = "회원가입 시 이메일 인증번호 전송")
    @GetMapping(value = "/{userEmail}")
    public ResponseEntity<SingleResponseDto<String>> sendUserEmailNumber(@PathVariable String userEmail) {
        String userEmailNumber = userService.sendUserEmailNumber(userEmail);

        return ResponseEntity.ok(new SingleResponseDto<>(200, "이메일 인증번호 전송 완료", userEmailNumber));
    }

    @ApiOperation(value = "로그인", notes = "이메일, 비밀번호로 로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<SingleResponseDto<String>> login(LoginRequestDto loginRequestDto) {
        String result = userService.login(loginRequestDto);
        if (result.equals("fail")) {
            return ResponseEntity.status(401).body(new SingleResponseDto<String>(401, "아이디와 비밀번호를 확인해주세요.", null));
        }

        return ResponseEntity.ok(new SingleResponseDto<>(200, "로그인 성공", result));
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기 시 새 비밀번호를 이메일에 전송한다.")
    @PutMapping(value = "/findpassword/{userEmail}")
    public ResponseEntity<BaseResponseDto> findUserPassword(@PathVariable String userEmail) {
        String result = userService.findUserPassword(userEmail);
        if (result.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "이메일을 확인해주세요."));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "새 비밀번호 전송 완료"));
    }

    @ApiOperation(value = "회원 탈퇴", notes = "userId로 회원정보를 삭제한다.")
    @Transactional  // org.springframework.dao.InvalidDataAccessApiUsageException 처리
    // Transaction이 Required 되지 않아서 발생하는 것
    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<BaseResponseDto> deleteUserInfo(@PathVariable long userId) {
        userRepository.deleteUserByUserId(userId);

        return ResponseEntity.ok(BaseResponseDto.of(200, "회원 탈퇴 성공"));
    }

    //    @ApiOperation(value = "회원정보 수정", notes = "유저의 정보를 수정한다.")
//    @PutMapping("/update")
//    public ResponseEntity<BaseResponseDto> updateUser(@ApiIgnore Authentication authentication, UserDto userDto){
//        String result = userService
//    }

//    @ApiOperation(value = "비밀번호 확인", notes = "회원정보 수정 전 비밀번호 확인을 한다.")
//    @PostMapping(value = "/checkpw")

//    @ApiOperation(value = "회원정보 조회", notes = "유저의 정보를 조회한다.")
//    @GetMapping("/info")
//    public ResponseEntity<BaseResponseDto> deleteUser(@ApiIgnore Authentication authentication)


}
