package com.ssafy.andback.api.controller;

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

}
