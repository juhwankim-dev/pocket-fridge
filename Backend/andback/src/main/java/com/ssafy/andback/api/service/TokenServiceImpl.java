package com.ssafy.andback.api.service;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.request.TokenRequestDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.core.domain.Token;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.TokenRepository;
import com.ssafy.andback.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
*
* TokenServiceImpl
* 토큰 서비스 구현체
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-29
* 마지막 수정일 2022-04-29
**/

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = false)
    public String insertToken(TokenRequestDto tokenDto) {
        Optional<User> user = userRepository.findByUserEmail(tokenDto.getUserEmail());
        user.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        Optional<List<Token>> tokenList = tokenRepository.findAllByUser(user.get());

        for (Token temp : tokenList.get()) {
            if (temp.getTokenDevice().equals(tokenDto.getTokenDeviceNum())) {
                temp.updateToken(tokenDto.getTokenNum());
                return "success";
            }
        }

        Token token = Token.builder().tokenNum(tokenDto.getTokenNum())
                .tokenDevice(tokenDto.getTokenDeviceNum())
                .tokenNum(tokenDto.getTokenNum())
                .user(user.get())
                .build();


        tokenRepository.save(token);

        return "success";
    }
}
