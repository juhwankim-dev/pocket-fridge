package com.ssafy.andback.api.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.request.InsertRefrigeratorRequestDto;
import com.ssafy.andback.api.dto.request.InsertShareMemberRequestDto;
import com.ssafy.andback.api.dto.response.RefrigeratorResponseDto;
import com.ssafy.andback.api.dto.response.RefrigeratorShareUserResponseDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.core.domain.*;
import com.ssafy.andback.core.queryrepository.RefrigeratorQueryRepository;
import com.ssafy.andback.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * RefrigeratorServiceImpl
 * 냉장고 서비스 구현체
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-25
 * 마지막 수정일 2022-04-25
 **/

@Service
@Transactional
@RequiredArgsConstructor
public class RefrigeratorServiceImpl implements RefrigeratorService {

    private final RefrigeratorRepository refrigeratorRepository;
    private final UserRefrigeratorRepository userRefrigeratorRepository;
    private final FoodIngredientRepository foodIngredientRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final NotificationRepository notificationRepository;

    public String insertRefrigerator(User user, String refrigeratorName) {


        Refrigerator refrigerator = refrigeratorRepository.save(Refrigerator.builder()
                .refrigeratorName(refrigeratorName)
                .build());

        UserRefrigerator save = userRefrigeratorRepository.save(UserRefrigerator.builder()
                .refrigerator(refrigerator)
                .refrigeratorOwner(true)
                .user(user)
                .build());

        return "success";
    }

    @Override
    public List<RefrigeratorResponseDto> findAllRefrigeratorByUser(User user) {

        List<UserRefrigerator> userRefrigeratorByUser = userRefrigeratorRepository.findUserRefrigeratorByUser(user);
        List<RefrigeratorResponseDto> response = new ArrayList<>();

        for (UserRefrigerator temp : userRefrigeratorByUser) {
            response.add(
                    RefrigeratorResponseDto.builder()
                            .refrigeratorId(temp.getRefrigerator().getRefrigeratorId())
                            .refrigeratorName(temp.getRefrigerator().getRefrigeratorName())
                            .refrigeratorOwner(temp.isRefrigeratorOwner()) // 주인 여부 추가 2022-05-13
                            .build());
        }

        return response;
    }

    @Override
    public String createShareGroup(User user, InsertShareMemberRequestDto insertShareMemberRequestDto) {

        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(insertShareMemberRequestDto.getRefrigeratorId());

        refrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND)
        );

        UserRefrigerator shareRefrigerator = new UserRefrigerator();

        shareRefrigerator.setRefrigerator(refrigerator.get());
        shareRefrigerator.setUser(user);
        shareRefrigerator.setRefrigeratorOwner(false);

        return "success";
    }

    @Override
    @Transactional(readOnly = false)
    public String updateRefrigerator(User user, Long refrigeratorId, String refrigeratorName) throws CustomException {

        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);

        refrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND)
        );

        Optional<UserRefrigerator> result = userRefrigeratorRepository.findByRefrigeratorAndUserAndRefrigeratorOwner(refrigerator.get(), user, true);
        result.orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_USER)
        );

        result.get().getRefrigerator().updateName(refrigeratorName);

        return "success";
    }

    @Override
    @Transactional(readOnly = false)
    public String deleteRefrigerator(User user, Long refrigeratorId) {


        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);

        refrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND)
        );

        Optional<UserRefrigerator> userRefrigerator = userRefrigeratorRepository.findByRefrigeratorAndUser(refrigerator.get(), user);

        userRefrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_USER)
        );

        if (userRefrigerator.get().isRefrigeratorOwner()) { // 주인일때
            //1. 냉장고 재료 모두 삭제
            foodIngredientRepository.deleteAll(userRefrigerator.get().getRefrigerator().getFoodIngredientList());

            //2. userRefrigerator 삭제
            userRefrigeratorRepository.deleteAll(userRefrigeratorRepository.findAllByRefrigerator(refrigerator.get()));

            //3. 냉장고 삭제
            refrigeratorRepository.delete(refrigerator.get());

        } else { // 주인이 아닐때
            //1. userRefrigerator삭제
            userRefrigeratorRepository.delete(userRefrigerator.get());
        }


        return "success";
    }

    @Override
    public List<RefrigeratorShareUserResponseDto> shareUserList(User user, Long refrigeratorId) {

        //예외처리 현재 유저가 가지고 있지 않는 냉장고이면 보여주지 않는다
        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);
        refrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND) // 냉장고가 없을 시 예외처리
        );

        // 없으면 권한이 없는 사용자 입니다
        if (!userRefrigeratorRepository.existsByRefrigeratorAndUser(refrigerator.get(), user)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }


        return userRefrigeratorRepository.shareUserList(refrigeratorId);
    }

    @Override
    public boolean deleteUser(User user, Long refrigeratorId, String userEmail) {

        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);

        refrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND)
        );

        Optional<User> targetUser = userRepository.findByUserEmail(userEmail);

        targetUser.orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 소유자인지 확인
        if (!userRefrigeratorRepository.existsByRefrigeratorAndUserAndRefrigeratorOwner(refrigerator.get(), user, true)) {
            //소유자가 아니면 처리하는 부분
            throw new CustomException(ErrorCode.INVALID_USER); // 권한 없는 유저입니다
        }


        Optional<UserRefrigerator> result = userRefrigeratorRepository.findByRefrigeratorAndUser(refrigerator.get(), targetUser.get());

        if (result.isEmpty()) {
            return false;
        }

        userRefrigeratorRepository.delete(result.get());

        Optional<List<Token>> tokenList = tokenRepository.findAllByUser(targetUser.get());

        String body = user.getUserNickname() + "님의 " + refrigerator.get().getRefrigeratorName() + "에서 추방당하셨습니다";

        Notification notification = Notification.builder().notificationMessage(body)
                .user(targetUser.get())
                .notificationRead(false)
                .refrigeratorId(refrigeratorId)
                .build();

        notificationRepository.save(notification);

        for (Token token : tokenList.get()) {
            firebaseCloudMessageService.sendMessageTo(token.getTokenNum(), "추방 알림", body);
        }

        return true;
    }
}
