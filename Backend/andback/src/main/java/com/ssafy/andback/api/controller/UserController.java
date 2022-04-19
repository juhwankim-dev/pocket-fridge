package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
*
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

    @ApiOperation("회원가입")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDto> signUp(UserDto userDto){
        String result = userService.insertUser(userDto);

        System.out.println(result);

        if(result.equals("fail")){
            return ResponseEntity.status(401).body(new BaseResponseDto(401, "회원가입 실패"));
        }
        return ResponseEntity.ok(new BaseResponseDto(200, "회원가입 성공"));
    }
}
