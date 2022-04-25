package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.response.RefrigeratorResDto;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.queryrepository.RefrigeratorQueryRepository;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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

    public String insertRefrigerator(String refrigeratorName) {

        if (refrigeratorName.equals("")) return "false";

        Refrigerator refrigerator = Refrigerator.builder().refrigeratorName(refrigeratorName).build();
        Refrigerator save = refrigeratorRepository.save(refrigerator);

        return "success";
    }

    @Override
    public List<RefrigeratorResDto> findAllRefrigeratorByUser(String userEmail) {

        User user = userRepository.findByUserEmail(userEmail);

        List<Refrigerator> refrigeratorByUser = refrigeratorQueryRepository.findAllRefrigeratorByUser(user);
        List<RefrigeratorResDto> response = new ArrayList<>();

        for (Refrigerator temp : refrigeratorByUser) {
            response.add(RefrigeratorResDto.builder()
                    .refrigeratorId(temp.getRefrigeratorId())
                    .refrigeratorName(temp.getRefrigeratorName())
                    .build());
        }

        return response;
    }


}
