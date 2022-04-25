package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.core.domain.User;

/**
 * UserService
 * UserController 에서 받은 데이터를 처리 (인터페이스)
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-04-19
 **/
public interface UserService {
    String insertUser(UserDto userDto);
    String checkUserEmail(String userEmail);
}
