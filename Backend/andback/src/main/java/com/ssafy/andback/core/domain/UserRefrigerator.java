package com.ssafy.andback.core.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * UserRefrigerator
 * user_refrigerator
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-25
 * 마지막 수정일 2022-04-25
 **/

@Entity
@Table(name = "user_refrigerator")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserRefrigerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_refrigerator")
    private Long userRefrigeratorId;

    // 냉장고(냉장고 1 : 유저 냉장고 N)
    @ManyToOne(targetEntity = Refrigerator.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "refrigerator_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Refrigerator refrigerator;

    // 유저(유저 1 : 유저 냉장고 N)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private User user;

    @Builder
    public UserRefrigerator(Refrigerator refrigerator, User user) {
        this.refrigerator = refrigerator;
        this.user = user;
    }
}
