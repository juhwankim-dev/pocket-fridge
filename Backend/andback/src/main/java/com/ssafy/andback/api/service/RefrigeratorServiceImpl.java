package com.ssafy.andback.api.service;

import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
*
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

    public String insertRefrigerator(String refrigeratorName) {

        if (refrigeratorName.equals("")) return "false";

        Refrigerator refrigerator = Refrigerator.builder().refrigeratorName(refrigeratorName).build();
        Refrigerator save = refrigeratorRepository.save(refrigerator);

        return "success";
    }


}
