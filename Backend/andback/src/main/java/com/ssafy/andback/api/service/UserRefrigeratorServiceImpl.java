package com.ssafy.andback.api.service;

import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.repository.UserRefrigeratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * RefrigeratorServiceImpl
 * 유저 냉장고 서비스 구현체
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-25
 * 마지막 수정일 2022-04-25
 **/

@Service
@RequiredArgsConstructor
public class UserRefrigeratorServiceImpl implements UserRefrigeratorService{

    private final UserRefrigeratorRepository userRefrigeratorRepository;

    @Override
    public String insertUserRefrigerator(User user, Refrigerator refrigerator) {

        UserRefrigerator userRefrigerator = UserRefrigerator.builder().refrigerator(refrigerator).user(user).build();

        UserRefrigerator save = userRefrigeratorRepository.save(userRefrigerator);

        return "false";
    }
}
