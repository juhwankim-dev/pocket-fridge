package com.ssafy.andback.api.service;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.request.InsertRefrigeratorRequestDto;
import com.ssafy.andback.api.dto.request.InsertShareMemberRequestDto;
import com.ssafy.andback.api.dto.response.RefrigeratorResponseDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.queryrepository.RefrigeratorQueryRepository;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UserRepository userRepository;
    private final UserRefrigeratorRepository userRefrigeratorRepository;

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
                            .refrigeratorId(temp.getUserRefrigeratorId())
                            .refrigeratorName(temp.getRefrigerator().getRefrigeratorName())
                            .refrigeratorOwner(temp.isRefrigeratorOwner()) // 주인 여부 추가 2022-05-13
                            .build());
        }

        return response;
    }

    @Override
    public String createShareGroup(User user, Long refrigeratorId) {

        Refrigerator refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);

        UserRefrigerator shareRefrigerator = new UserRefrigerator();

        shareRefrigerator.setRefrigerator(refrigerator);
        shareRefrigerator.setUser(user);
        shareRefrigerator.setRefrigeratorOwner(true);

        return "success";
    }

    @Override
    public String createShareGroup(User user, InsertShareMemberRequestDto insertShareMemberRequestDto) {

        Refrigerator refrigerator = refrigeratorRepository.findByRefrigeratorId(insertShareMemberRequestDto.getRefrigeratorId());

        UserRefrigerator shareRefrigerator = new UserRefrigerator();

        shareRefrigerator.setRefrigerator(refrigerator);
        shareRefrigerator.setUser(user);
        shareRefrigerator.setRefrigeratorOwner(false);

        return "success";
    }

    @Override
    @Transactional(readOnly = false)
    public String updateRefrigerator(User user, Long refrigeratorId, String refrigeratorName) {

        Refrigerator refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);

        if (refrigerator == null) {
            throw new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND);
        }

        Optional<UserRefrigerator> result = userRefrigeratorRepository.findAllByRefrigeratorAndUserAndRefrigeratorOwner(refrigerator, user, true);
        result.orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_USER)
        );

        result.get().getRefrigerator().updateName(refrigeratorName);

        return "success";
    }

}
