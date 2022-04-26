package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.dto.request.LoginRequestDto;
import com.ssafy.andback.api.dto.response.LoginResponseDto;
import com.ssafy.andback.api.dto.response.UserEmailNumberResponseDto;
import io.swagger.annotations.*;
import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
    public ResponseEntity<BaseResponseDto> sendUserEmailNumber(@PathVariable String userEmail) {
        String userEmailNumber = userService.sendUserEmailNumber(userEmail);

        return ResponseEntity.ok(UserEmailNumberResponseDto.of(200, "이메일 인증번호 전송 완료", userEmailNumber));
    }

    @ApiOperation(value = "로그인", notes = "이메일, 비밀번호로 로그인")
    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponseDto> login(LoginRequestDto loginRequestDto){
        String result = userService.login(loginRequestDto);
        if(result.equals("fail")){
            return ResponseEntity.status(401).body(LoginResponseDto.of(401, "아이디와 비밀번호를 확인해주세요.", null));
        }
//        return ResponseEntity.ok(LoginResponseDto.of(200, "로그인 성공", ));
        return ResponseEntity.ok(BaseResponseDto.of(200, "로그인 성공"));
    }

    @ApiOperation(value = "비밀번호 찾기", notes = "비밀번호 찾기 시 새 비밀번호를 이메일에 전송한다.")
    @PutMapping(value = "/findpassword/{userEmail}")
    public ResponseEntity<BaseResponseDto> findUserPassword(@PathVariable String userEmail){
        String result = userService.findUserPassword(userEmail);
        if(result.equals("fail")){
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "이메일을 확인해주세요."));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "새 비밀번호 전송 완료"));
    }

//    @ApiOperation(value = "회원 탈퇴", notes = "회원을 탈퇴한다")
//    @DeleteMapping(value = "/delete")
//    public ResponseEntity<BaseResponseDto> deleteUser(@PathVariable User)

//    @ApiOperation(value = "회원정보 수정", notes = "유저의 정보를 수정한다.")
//    @PutMapping("/update")
//    public ResponseEntity<BaseResponseDto> updateUser(UserDto userDto){
//        String result = userService
//    }


}
