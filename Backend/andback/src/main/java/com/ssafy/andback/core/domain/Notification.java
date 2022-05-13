package com.ssafy.andback.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

/**
 * Notification
 * 알림기능 Entity
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-13
 * 마지막 수정일 2022-05-13
 **/

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    @NotNull
    private Long notificationId;

    @Column(name = "check_read")
    @NotNull
    private Boolean notificationRead;

    @Column(name = "notification_message")
    @NotNull
    private String notificationMessage;

    @Column(name = "refrigerator_id")
    private Long refrigeratorId;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    public void updateNotificationRead() {
        this.notificationRead = true;
    }

    @Builder
    public Notification(@NotNull Boolean notificationRead, @NotNull String notificationMessage, Long refrigeratorId, @NotNull User user) {
        this.notificationRead = notificationRead;
        this.notificationMessage = notificationMessage;
        this.refrigeratorId = refrigeratorId;
        this.user = user;
    }
}
