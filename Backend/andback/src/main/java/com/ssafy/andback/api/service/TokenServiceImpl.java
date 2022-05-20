package com.ssafy.andback.api.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.request.TokenRequestDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.core.domain.*;
import com.ssafy.andback.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

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
    private final UserRefrigeratorRepository userRefrigeratorRepository;


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
    public void sendMessage(User user, String userEmail, Long refrigeratorId) throws CustomException {

        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);

        // 냉장고 검사
        refrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND)
        );

        // 유저가 냉장고 소유자인지 검사
        if (!userRefrigeratorRepository.existsByRefrigeratorAndUserAndRefrigeratorOwner(refrigerator.get(), user, true)) {
            // 소유자가 아니면 공유할 수 없음
            // 냉장고가 없어도 공유할 수 없음
            throw new CustomException(ErrorCode.INVALID_USER);
        }


        Optional<User> targetUser = userRepository.findByUserEmail(userEmail);

        // 유저 검사
        targetUser.orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // target 에게 냉장고가 있을 때
        // 이미 공유된 냉장고 입니다.
        if (userRefrigeratorRepository.existsByRefrigeratorAndUser(refrigerator.get(), targetUser.get())) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE); // 데이터가 이미 존재합니다
        }


        //알람에 넣어주기
        String message = user.getUserNickname() + "님이 " + refrigerator.get().getRefrigeratorName() + " 공유했습니다";

        Notification notification = Notification.builder()
                .notificationMessage(message)
                .notificationRead(false)
                .refrigeratorId(refrigeratorId)
                .user(targetUser.get())
                .build();

        notificationRepository.save(notification);

        Optional<List<Token>> TokenList = tokenRepository.findAllByUser(targetUser.get());
        for (Token temp : TokenList.get()) {
            firebaseCloudMessageService.sendMessageTo(temp.getTokenNum(), "냉장고 공유", message);
        }

        //냉장고 공유 세이브
        UserRefrigerator save = new UserRefrigerator(refrigerator.get(), targetUser.get(), false);
        userRefrigeratorRepository.save(save);
    }


}
