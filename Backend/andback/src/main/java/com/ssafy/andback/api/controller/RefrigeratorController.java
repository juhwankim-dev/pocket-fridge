package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.request.InsertRefrigeratorRequestDto;
import com.ssafy.andback.api.dto.request.InsertShareMemberRequestDto;
import com.ssafy.andback.api.dto.response.*;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.api.service.RefrigeratorService;
import com.ssafy.andback.api.service.TokenService;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.TokenRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


import java.io.IOException;
import java.util.List;

/**
 * RefrigeratorController
 * 냉장공 API 컨트롤러
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-25
 * 마지막 수정일 2022-04-25
 **/

@Api(tags = {"03. 냉장고 API"})
@RestController()
@RequestMapping(value = "/refrigerator")
@RequiredArgsConstructor
public class RefrigeratorController {

    private final RefrigeratorService refrigeratorService;
    private final TokenService tokenService;

    @ApiOperation(value = "냉장고 조회", notes = "사용자의 JWT 토큰값을 받아 냉장고 리스트를 보여준다")
    @GetMapping
    public ResponseEntity<ListResponseDto<RefrigeratorResponseDto>> findRefrigeratorList(@ApiIgnore Authentication authentication) {

        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }


        List<RefrigeratorResponseDto> response = refrigeratorService.findAllRefrigeratorByUser(user);

        return ResponseEntity.ok(new ListResponseDto<RefrigeratorResponseDto>(200, "success", response));
    }

    @ApiOperation(value = "냉장고 생성", notes = "사용자의 새로운 냉장고를 생성한다")
    @PostMapping("/{refrigeratorName}")
    public ResponseEntity<BaseResponseDto> insertRefrigerator(@ApiIgnore Authentication authentication, @PathVariable String refrigeratorName) {

        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        String response = refrigeratorService.insertRefrigerator(user, refrigeratorName);


        if (response.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "실패"));
        }

        return ResponseEntity.ok(BaseResponseDto.of(200, "success"));
    }

    @ApiOperation(value = "냉장고 이름 변경", notes = "냉장고 이름을 변경한다")
    @PutMapping("/{refrigeratorId}/{refrigeratorName}")
    public ResponseEntity<BaseResponseDto>
    updateRefrigerator(@ApiIgnore Authentication authentication, @PathVariable Long refrigeratorId, @PathVariable String refrigeratorName)
            throws CustomException {

        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        refrigeratorService.updateRefrigerator(user, refrigeratorId, refrigeratorName);

        return ResponseEntity.ok(BaseResponseDto.of(200, "success"));
    }

    @ApiOperation(value = "냉장고 삭제", notes = "냉장고를 삭제한다")
    @DeleteMapping("/{refrigeratorId}")
    public ResponseEntity<BaseResponseDto>
    deleteRefrigerator(@ApiIgnore Authentication authentication, @PathVariable Long refrigeratorId) throws CustomException {
        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        refrigeratorService.deleteRefrigerator(user, refrigeratorId);

        return ResponseEntity.ok(BaseResponseDto.of(200, "success"));
    }

    @ApiOperation(value = "냉장고 공유", notes = "공유할 대상에게 냉장고를 공유하고 메세지를 보내준다")
    @GetMapping("/share/{userEmail}/{refrigeratorId}")
    public ResponseEntity<SingleResponseDto<String>> inviteShareMember(@ApiIgnore Authentication authentication, @PathVariable String userEmail, @PathVariable Long refrigeratorId) throws CustomException, IOException {

        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        tokenService.sendMessage(user, userEmail, refrigeratorId);

        return ResponseEntity.ok(new SingleResponseDto<String>(200, "success", "메세지 전달 완료"));
    }

    @ApiOperation(value = "공유 그룹원 초대 수락", notes = "공유 그룹원을 추가한다.")
    @PostMapping("/share/insertmember")
    public ResponseEntity<BaseResponseDto> insertShareMember(@ApiIgnore Authentication authentication, @RequestBody InsertShareMemberRequestDto insertShareMemberRequestDto) {

        User user = (User) authentication.getPrincipal();

        String result = refrigeratorService.createShareGroup(user, insertShareMemberRequestDto);

        if (result.equals("success")) {
            return ResponseEntity.ok().body(BaseResponseDto.of(200, "냉장고 공유 그룹원 추가 성공"));
        }

        throw new CustomException(ErrorCode.FAIL_SHARE_GROUP);
    }

    @ApiOperation(value = "공유 냉장고 유저 목록", notes = "공유 냉장고의 그룹원을 모두 보여준다")
    @GetMapping("/share/{refrigeratorId}")
    public ResponseEntity<ListResponseDto<RefrigeratorShareUserResponseDto>> shareUserList(@ApiIgnore Authentication authentication, @PathVariable Long refrigeratorId) {

        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }


        List<RefrigeratorShareUserResponseDto> response = refrigeratorService.shareUserList(user, refrigeratorId);

        return ResponseEntity.ok().body(new ListResponseDto<RefrigeratorShareUserResponseDto>(200, "success", response));
    }

    @ApiOperation(value = "공유 대상 추방", notes = "대상을 공유 냉장고 목록에서 추방한다")
    @DeleteMapping("/{refrigeratorId}/{userEmail}")
    public ResponseEntity<SingleResponseDto<String>> deleteUser(@ApiIgnore Authentication authentication, @PathVariable Long refrigeratorId, @PathVariable String userEmail) throws IOException {
        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        if (refrigeratorService.deleteUser(user, refrigeratorId, userEmail)) {
            return ResponseEntity.ok().body(new SingleResponseDto<String>(200, "success", "추방에 성공했습니다"));
        }
        return ResponseEntity.ok().body(new SingleResponseDto<String>(401, "success", "추방에 실패했습니다"));

    }

}
