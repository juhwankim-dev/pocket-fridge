package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.request.TokenRequestDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.api.service.TokenService;
import com.ssafy.andback.core.domain.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * TokenController
 * 토큰 컨트롤러
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-29
 * 마지막 수정일 2022-04-29
 **/

@Api(tags = {"06. 토큰 API"})
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping()
    public ResponseEntity<BaseResponseDto> insertToken(@ApiIgnore Authentication authentication, @RequestBody TokenRequestDto reqDto) {

        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();


        String result = tokenService.insertToken(user, reqDto);

        if (result == "success") {
            return ResponseEntity.ok().body(BaseResponseDto.of(200, "토큰 저장에 성공했습니다."));
        }

        throw new CustomException(ErrorCode.FAIL_SAVE_TOKEN);
    }

}
