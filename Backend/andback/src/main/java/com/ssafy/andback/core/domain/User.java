package com.ssafy.andback.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * User
 * 실제 DB 테이블과 매핑되는 유저 객체 생성
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-04-19
 **/

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor // 파라미터 없는 기본 생성자 자동 생성 (lombok 어노테이션)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email", unique = true)
    private String userEmail;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_nickname", unique = true)
    private String userNickname;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_picture")
    private String userPicture;

    @Column(name = "user_login_type", nullable = false)
    private Boolean userLoginType;

    @Builder
    public User(String userEmail, String userName, String userNickname, String userPassword, String userPicture, Boolean userLoginType) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
        this.userPicture = userPicture;
        this.userLoginType = userLoginType;
    }

    public User update(String userNickname, String userPassword, String userPicture) {
        this.userNickname = userNickname;
        this.userPassword = userPassword;
        this.userPicture = userPicture;

        return this;
    }

    // 연관관계 매핑 나중에
}
