package com.ssafy.andback.api.service;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.core.domain.Token;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.TokenRepository;
import com.ssafy.andback.core.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class TokenServiceTest {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void makeUser() {
        User makeUser = User.builder().userEmail("rladudgns456@naver.com")
                .userName("김영훈")
                .userNickname("김영훈")
                .userLoginType(false)
                .userPicture(null)
                .userPassword("1234")
                .build();

        user = userRepository.save(makeUser);

        System.out.println(user.getUserId());
    }


    @Test
    void 토큰저장하기(){

        //given
        Token galaxyS22 = Token.builder()
                .tokenNum("123")
                .tokenDevice("galaxyS22")
                .user(user)
                .build();

        //when
        Token save = tokenRepository.save(galaxyS22);
        Optional<Token> result = tokenRepository.findById(save.getTokenId());

        //then
        assertThat(save.getTokenDevice()).isEqualTo(result.get().getTokenDevice());
    }

}