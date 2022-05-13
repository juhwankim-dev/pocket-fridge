package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.constant.LoginType;
import com.ssafy.andback.api.constant.SocialLoginType;
import com.ssafy.andback.api.dto.response.SingleResponseDto;
import com.ssafy.andback.api.dto.response.TokenResponseDto;
import com.ssafy.andback.api.service.OAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


/**
 * SocialController
 * 소셜 로그인 컨트롤럴
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-10
 * 마지막 수정일 2022-05-10
 **/

@Api(tags = {"08. 소셜 로그인 API"})
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SocialController {

    private final OAuthService oAuthService;

    @ApiOperation(value = "소셜 로그인", notes = "소셜 로그인 타입과 code 를 전송받아 소셜로그인을 진행한다")
    @ResponseBody
    @GetMapping(value = "/{socialLoginType}/callback")
    public ResponseEntity<SingleResponseDto<TokenResponseDto>> callback(
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code) throws Exception {

        SocialLoginType socialLoginType = SocialLoginType.valueOf(socialLoginPath.toUpperCase());

        String result = oAuthService.oAuthLogin(socialLoginType, code);

        TokenResponseDto response = TokenResponseDto.builder()
                .loginType(LoginType.valueOf(socialLoginPath.toUpperCase()))
                .jwtToken(result)
                .build();

        if (result == "fail") {
            return ResponseEntity.ok(new SingleResponseDto<TokenResponseDto>(200, "fail", null));
        }
        return ResponseEntity.ok(new SingleResponseDto<TokenResponseDto>(200, "로그인 성공", response));
    }

}
