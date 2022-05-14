package com.ssafy.andback.api.service;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.request.TokenRequestDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.core.domain.Notification;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.Token;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.NotificationRepository;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import com.ssafy.andback.core.repository.TokenRepository;
import com.ssafy.andback.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
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
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final RefrigeratorRepository refrigeratorRepository;
    private final NotificationRepository notificationRepository;


    @Override
    @Transactional(readOnly = false)

    public String insertToken(User user, TokenRequestDto tokenDto) {

        Optional<List<Token>> tokenList = tokenRepository.findAllByUser(user);

        for (Token temp : tokenList.get()) {
            if (temp.getTokenDevice().equals(tokenDto.getTokenDeviceNum())) {
                temp.updateToken(tokenDto.getTokenNum());
                return "success";
            }
        }

        Token token = Token.builder().tokenNum(tokenDto.getTokenNum())
                .tokenDevice(tokenDto.getTokenDeviceNum())
                .tokenNum(tokenDto.getTokenNum())
                .user(user)
                .build();

        tokenRepository.save(token);

        return "success";
    }

    @Override
    @Transactional(readOnly = false)
    public String sendMessage(String userEmail, Long refrigeratorId) throws IOException, CustomException {

        Optional<User> user = userRepository.findByUserEmail(userEmail);

        // 유저 검사
        user.orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);

        // 냉장고 검사
        refrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND)
        );

        //알람에 넣어주기

        String message = refrigerator.get().getRefrigeratorName() + "을 공유 받으시겠습니까?";

        Notification notification = Notification.builder()
                .notificationMessage(message)
                .notificationRead(false)
                .refrigeratorId(refrigeratorId)
                .user(user.get())
                .build();

        notificationRepository.save(notification);

        Optional<List<Token>> TokenList = tokenRepository.findAllByUser(user.get());
        for (Token temp : TokenList.get()) {
            firebaseCloudMessageService.sendMessageTo(temp.getTokenNum(), "냉장고 공유 수락 요청", message);
        }

        return null;
    }


}
