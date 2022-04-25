package com.ssafy.andback.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class UserRefrigerator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_refrigerator")
    private Long userRefrigeratorId;

    // 냉장고(냉장고 1 : 유저 냉장고 N)
    @ManyToOne(targetEntity = Refrigerator.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "refrigerator_id")
    private Refrigerator refrigerator;

    // 유저(유저 1 : 유저 냉장고 N)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public UserRefrigerator(Refrigerator refrigerator, User user) {
        this.refrigerator = refrigerator;
        this.user = user;
    }
}