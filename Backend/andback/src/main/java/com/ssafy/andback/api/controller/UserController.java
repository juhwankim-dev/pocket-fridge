package com.ssafy.andback.api.controller;

import io.swagger.annotations.*;
import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
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
 * 마지막 수정일 2022-04-19
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
    @GetMapping("/{userEmail}")
    public ResponseEntity<BaseResponseDto> checkEmail(@PathVariable String userEmail){
        String result = userService.checkUserEmail(userEmail);
        if(result.equals("fail")){
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "이메일 중복"));
        }

        return ResponseEntity.ok(BaseResponseDto.of(200, "이메일 사용 가능"));
    }

}
