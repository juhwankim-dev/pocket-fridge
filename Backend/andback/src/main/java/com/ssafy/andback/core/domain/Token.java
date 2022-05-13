package com.ssafy.andback.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
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
@NoArgsConstructor
@Table(name = "token")
public class Token extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "token_id")
    private Long tokenId;

    @Column(name = "token_device")
    private String tokenDevice;

    @Column(name = "token_num")
    private String tokenNum;


    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateToken(String tokenNum) {
        this.tokenNum = tokenNum;
    }

    @Builder
    public Token(String tokenDevice, String tokenNum, User user) {
        this.tokenDevice = tokenDevice;
        this.tokenNum = tokenNum;
        this.user = user;
    }
}
