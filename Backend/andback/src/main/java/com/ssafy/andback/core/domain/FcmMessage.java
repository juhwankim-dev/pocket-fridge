package com.ssafy.andback.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
*
* FcmMessage
* FcmMessgae 전송 Dto
*
* @author hoony
* @version 1.0.0
* 생성일 2022-05-03
* 마지막 수정일 2022-05-03
**/

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;
        private String image;
    }
}