package com.ssafy.andback.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_refrigerator")
@Getter
@Setter
public class UserRefrigerator {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
