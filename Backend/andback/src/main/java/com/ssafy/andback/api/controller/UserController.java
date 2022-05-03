package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.dto.request.CheckUserPasswordRequestDto;
import com.ssafy.andback.api.dto.request.FindUserPasswordRequestDto;
import com.ssafy.andback.api.dto.request.LoginRequestDto;
import com.ssafy.andback.api.dto.request.UpdateUserRequestDto;
import com.ssafy.andback.api.dto.response.CheckUserResponseDto;
import com.ssafy.andback.api.dto.response.SingleResponseDto;
import com.ssafy.andback.core.domain.User;
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
 * 마지막 수정일 2022-05-02
 **/

@RequiredArgsConstructor
@Api(value = "유저 API", tags = {"01. 유저 API"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "회원가입", notes = "유저 정보를 받아 DB에 저장한다. " +
            "이메일: 이메일 형식, 이름: 2~20자, 닉네임: 2~10자(특수문자 제외), 비밀번호(영문자 포함 5~15자), 사진: null 허용")
    @PostMapping
    public ResponseEntity<BaseResponseDto> signUp(@RequestBody @Valid UserDto userDto, @ApiIgnore Errors errors) {
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
    public ResponseEntity<SingleResponseDto<String>> login(@RequestBody LoginRequestDto loginRequestDto) {
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

    @ApiOperation(value = "회원 탈퇴", notes = "jwt 토큰을 받아 회원정보를 삭제한다.")
    @Transactional  // org.springframework.dao.InvalidDataAccessApiUsageException 처리
    // Transaction이 Required 되지 않아서 발생하는 것
    @DeleteMapping
    // Authentication 객체: 인증에 성공한 사용자의 정보를 가지고 있는 객체
    public ResponseEntity<BaseResponseDto> deleteUser(@ApiIgnore Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        userService.deleteUser(user);
        return ResponseEntity.ok(BaseResponseDto.of(200, "회원 탈퇴 성공"));
    }

    @ApiOperation(value = "회원정보 조회", notes = "유저의 정보를 조회한다.")
    @GetMapping
    public ResponseEntity<SingleResponseDto<CheckUserResponseDto>> checkUser(@ApiIgnore Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        CheckUserResponseDto checkUserResponseDto = CheckUserResponseDto.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail())
                .userName(user.getUserName())
                .userNickname(user.getUserNickname())
                .userPicture(user.getUserPicture())
                .build();

        return ResponseEntity.ok(new SingleResponseDto<CheckUserResponseDto>(200, "회원 정보 조회 성공", checkUserResponseDto));
    }

    @ApiOperation(value = "비밀번호 확인", notes = "회원정보 수정 전 비밀번호 확인을 한다.")
    @PostMapping(value = "/update")
    public ResponseEntity<BaseResponseDto> checkUserPassword(@ApiIgnore Authentication authentication,
                                                             @RequestBody CheckUserPasswordRequestDto checkUserPasswordRequestDto){
        User user = (User) authentication.getPrincipal();

        String answer = userService.checkUserPassword(user, checkUserPasswordRequestDto.getUserPassword());
        if(answer.equals("fail")){
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "비밀번호가 틀렸습니다."));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "비밀번호 확인 완료"));
    }

    @ApiOperation(value = "회원정보 수정", notes = "유저의 정보를 수정한다.")
    @PutMapping(value = "/update")
    public ResponseEntity<BaseResponseDto> updateUser(@ApiIgnore Authentication authentication,
                                                      @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto,
                                                      @ApiIgnore Errors errors){
        User user = (User) authentication.getPrincipal();

        String answer = userService.updateUser(user, updateUserRequestDto);
        if(answer.equals("fail")){
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "닉네임 중복을 확인하세요."));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "회원 정보 수정 완료"));
    }



}
