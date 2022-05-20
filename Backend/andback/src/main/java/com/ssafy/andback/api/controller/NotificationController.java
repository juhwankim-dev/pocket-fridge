package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.dto.response.ListResponseDto;
import com.ssafy.andback.api.dto.response.NotificationResponseDto;
import com.ssafy.andback.api.dto.response.SingleResponseDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.api.service.NotificationService;
import com.ssafy.andback.core.domain.BaseEntity;
import com.ssafy.andback.core.domain.Notification;
import com.ssafy.andback.core.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


/**
 * NotificationController
 * 알림 컨트롤러
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-13
 * 마지막 수정일 2022-05-13
 **/

@Api(tags = {"09. 알림 API"})
@RequiredArgsConstructor
@RequestMapping("/notification")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @ApiOperation(value = "유저 알림 조회", notes = "jwt 토큰을 받아 유저 알림 리스트를 보여준다")
    ResponseEntity<ListResponseDto<NotificationResponseDto>> findAllNotification(@ApiIgnore Authentication authentication) throws CustomException {
        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<NotificationResponseDto> response = notificationService.findAllByUser(user);

        return ResponseEntity.ok(new ListResponseDto<>(200, "success", response));
    }

    @PutMapping()
    @ApiOperation(value = "알림 읽기", notes = "읽지 않았던 알림은 모두 읽음 표시")
    ResponseEntity<BaseResponseDto> readNotifiacion(@ApiIgnore Authentication authentication) {
        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        String result = notificationService.readNotificationByUser(user);

        if (result != "success") {
            return ResponseEntity.ok(BaseResponseDto.of(404, "fail"));
        }

        return ResponseEntity.ok(BaseResponseDto.of(200, "success"));
    }

    @GetMapping("/read")
    @ApiOperation(value = "안 읽은 알림 조회", notes = "안 읽은 알림이 있는지 없는지 여부를 반환한다")
    ResponseEntity<SingleResponseDto<Boolean>> noReadNotification(@ApiIgnore Authentication authentication) {
        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Boolean response = notificationService.noReadNotification(user);

        return ResponseEntity.ok(new SingleResponseDto<Boolean>(200, "success", response));

    }
}
