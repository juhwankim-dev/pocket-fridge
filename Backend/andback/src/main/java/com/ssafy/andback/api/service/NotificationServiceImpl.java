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
    public List<NotificationResponseDto> findAllByUser(User user) {
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
        }

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public String readNotificationByUser(User user) {

        List<Notification> notificationList = notificationRepository.findAllByUserAndNotificationRead(user, false);

        for (Notification temp : notificationList) {
            temp.updateNotificationRead();
        }

        return "success";
    }

    @Override
    public Boolean noReadNotification(User user) {
        // 읽지 않는 notification 이 존재하면 true;
        // 없으면 false;
        return notificationRepository.existsByUserAndNotificationRead(user, false);
    }
}
