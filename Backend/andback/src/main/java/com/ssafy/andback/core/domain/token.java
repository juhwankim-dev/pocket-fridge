package com.ssafy.andback.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
*
* token
* Token Entity 생성!
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-28
* 마지막 수정일 2022-04-28
**/

@Entity
@Getter
@Setter
public class token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "token_id")
    private Long tokenId;

    @Column(name = "token_device")
    private String tokenDevice;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
}
