package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * NotificationResponseDto
 * 알림 전송 Dto
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-13
 * 마지막 수정일 2022-05-13
 **/

@Data
public class NotificationResponseDto {

    //알림 번호
    private Long notificationId;

    //읽은 처리
    private Boolean notificationRead;

    //냉장고 메세지
    private String notificationMessage;

    //냉장고 번호
    private Long refrigeratorId;

    //알림 시간
    //private String messageTime;

    @Builder
    public NotificationResponseDto(Long notificationId, Boolean notificationRead, String notificationMessage, Long refrigeratorId) {
        this.notificationId = notificationId;
        this.notificationRead = notificationRead;
        this.notificationMessage = notificationMessage;
        this.refrigeratorId = refrigeratorId;
    }
}
