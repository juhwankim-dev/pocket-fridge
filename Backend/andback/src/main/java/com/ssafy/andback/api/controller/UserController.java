package com.ssafy.andback.api.controller;

import io.swagger.annotations.Api;
import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    // Swagger UI : API 설명 추가
    @ApiOperation(value = "회원가입", notes = "유저 정보를 받아 DB에 저장한다.")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "userEmail",
                            value = "유저 이메일",
                            required = true
                    ),
                    @ApiImplicitParam(
                            name = "userName",
                            value = "유저 이름",
                            required = true
                    ),
                    @ApiImplicitParam(
                            name = "userNickname",
                            value = "유저 닉네임",
                            required = true
                    ),
                    @ApiImplicitParam(
                            name = "userPassword",
                            value = "유저 비밀번호",
                            required = true
                    ),
                    @ApiImplicitParam(
                            name = "userPicture",
                            value = "유저 사진 (null 가능)"
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<BaseResponseDto> signUp(UserDto userDto){
        String result = userService.insertUser(userDto);

        if(result.equals("fail")){
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "회원가입 실패"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "회원가입 성공"));
    }
}
