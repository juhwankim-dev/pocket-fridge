package com.ssafy.andback.api.service;

import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.repository.UserRefrigeratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserRefrigeratorServiceImpl implements UserRefrigeratorService{

    private final UserRefrigeratorRepository userRefrigeratorRepository;

    @Override
    public List<UserRefrigerator> findUserRefrigeratorsByUser(User user) {

        List<UserRefrigerator> userRefrigeratorList = userRefrigeratorRepository.findUserRefrigeratorByUser(user);
        List<UserRefrigerator> userRefrigerators = new ArrayList<>();

        for(int i=0; i<userRefrigeratorList.size(); i++) {
            userRefrigerators.add(UserRefrigerator.builder()
                    .refrigerator(userRefrigeratorList.get(i).getRefrigerator())
                    .user(userRefrigeratorList.get(i).getUser())
                    .build());
        }

        return userRefrigerators;
    }
}

