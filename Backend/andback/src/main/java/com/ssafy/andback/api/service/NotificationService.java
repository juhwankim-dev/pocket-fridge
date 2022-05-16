package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.response.NotificationResponseDto;
import com.ssafy.andback.core.domain.User;

import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> findAllByUser(User user);

    String readNotificationByUser(User user);

    Boolean noReadNotification(User user);
}
