package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.response.NotificationResponseDto;
import com.ssafy.andback.core.domain.Notification;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponseDto> findAllbyUser(User user) {
        // 최신순으로 리스트를 받는다
        List<Notification> notificationList = notificationRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<NotificationResponseDto> result = new ArrayList<>();

        for (Notification temp : notificationList) {
            result.add(NotificationResponseDto.builder()
                    .notificationId(temp.getNotificationId())
                    .notificationMessage(temp.getNotificationMessage())
                    .refrigeratorId(temp.getRefrigeratorId())
                    .notificationRead(temp.getNotificationRead())
                    .build());

            //읽음 처리
            temp.updateNotificationRead();
        }

        return result;
    }
}
