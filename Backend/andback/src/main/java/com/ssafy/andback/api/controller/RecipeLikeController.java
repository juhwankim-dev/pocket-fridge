/**
* RecipeLikeController
* 레시피 좋아요 컨트롤러
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/05/02
* 마지막 수정일 2022/05/02
**/
package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.api.service.RecipeLikeService;
import com.ssafy.andback.core.domain.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = {"05. 좋아요 API"})
@RestController
@RequestMapping(value = "/like")
@RequiredArgsConstructor
public class RecipeLikeController {

    private final RecipeLikeService recipeLikeService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<BaseResponseDto> addLike(@ApiIgnore Authentication authentication, @PathVariable Long recipeId) {

        User user = (User) authentication.getPrincipal();
        boolean res = false;

        res = recipeLikeService.addLike(user, recipeId);

        if(res) {
            return ResponseEntity.ok().body(BaseResponseDto.of(200, "좋아요 성공"));
        }

        throw new CustomException(ErrorCode.FAIL_LIKE);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<BaseResponseDto> removeLike(@ApiIgnore Authentication authentication, @PathVariable Long recipeId) {

        User user = (User) authentication.getPrincipal();
        boolean res = false;

         res = recipeLikeService.removeLike(user, recipeId);

        if(res) {
            return ResponseEntity.ok().body(BaseResponseDto.of(200, "좋아요 삭제 성공"));
        }

        throw new CustomException(ErrorCode.FAIL_REMOVE_LIKE);
    }
}