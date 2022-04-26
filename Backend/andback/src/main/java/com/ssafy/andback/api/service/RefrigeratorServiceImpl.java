package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.request.InsertRefrigeratorRequestDto;
import com.ssafy.andback.api.dto.response.RefrigeratorResponseDto;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.queryrepository.RefrigeratorQueryRepository;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class RefrigeratorServiceImpl implements RefrigeratorService {

    private final RefrigeratorRepository refrigeratorRepository;
    private final RefrigeratorQueryRepository refrigeratorQueryRepository;
    private final UserRepository userRepository;
    private final UserRefrigeratorRepository userRefrigeratorRepository;

    public String insertRefrigerator(InsertRefrigeratorRequestDto reqDto) {

        Optional<User> user = userRepository.findByUserEmail(reqDto.getUserEmail());
        if (!user.isPresent()) {
            return "fail";
        }

        Refrigerator refrigerator = refrigeratorRepository.save(Refrigerator.builder()
                .refrigeratorName(reqDto.getRefrigeratorName())
                .build());

        UserRefrigerator save = userRefrigeratorRepository.save(UserRefrigerator.builder()
                .refrigerator(refrigerator)
                .user(user.get())
                .build());

        return "success";
    }

    @Override
    public List<RefrigeratorResponseDto> findAllRefrigeratorByUser(String userEmail) {

        Optional<User> user = userRepository.findByUserEmail(userEmail);
        if (!user.isPresent()) {
            return null;
        }

        List<Refrigerator> refrigeratorByUser = refrigeratorQueryRepository.findAllRefrigeratorByUser(user.get());
        List<RefrigeratorResponseDto> response = new ArrayList<>();

        for (Refrigerator temp : refrigeratorByUser) {
            response.add(RefrigeratorResponseDto.builder()
                    .refrigeratorId(temp.getRefrigeratorId())
                    .refrigeratorName(temp.getRefrigeratorName())
                    .build());
        }

        return response;
    }

}
