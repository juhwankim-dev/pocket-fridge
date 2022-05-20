package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.Notification;
import com.ssafy.andback.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * NotificationRepository
 * 알람기능 저장소
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-13
 * 마지막 수정일 2022-05-13
 **/

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserOrderByCreatedDateDesc(User user);

    List<Notification> findAllByUserAndNotificationRead(User user, boolean read);

    void deleteAllByUser(User user);

    Boolean existsByUserAndNotificationRead(User user, boolean notificationRead);
}
