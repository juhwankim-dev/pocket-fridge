package com.ssafy.andback.api.service;

import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.User;

/**
 *
 * RefrigeratorServiceImpl
 * 유저 냉장고 서비스 인터페이스
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-25
 * 마지막 수정일 2022-04-25
 **/

public interface UserRefrigeratorService {

    public String insertUserRefrigerator(User user, Refrigerator refrigerator);

}
