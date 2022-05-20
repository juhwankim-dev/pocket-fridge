package com.ssafy.andback.api.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.andback.api.dto.request.FcmMessageRequestDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.service.FirebaseCloudMessageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * FcmMessageController
 * fcm 메세지 전송 컨트롤러
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-03
 * 마지막 수정일 2022-05-03
 **/

@RestController
@RequestMapping("/fcm")
@RequiredArgsConstructor
@Api(tags = {"07. FCM API"})
public class FcmMessageController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping
    public ResponseEntity<BaseResponseDto> pushMessage(@RequestBody FcmMessageRequestDto requestDto) {

        firebaseCloudMessageService.sendMessageTo(
                requestDto.getTargetToken(),
                requestDto.getTitle(),
                requestDto.getBody()
        );

        return ResponseEntity.ok().body(BaseResponseDto.of(200, "메시지 성공"));
    }

}
