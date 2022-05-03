package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.api.dto.request.FindUserPasswordRequestDto;
import com.ssafy.andback.api.dto.request.LoginRequestDto;
import com.ssafy.andback.api.dto.request.UpdateUserRequestDto;
import com.ssafy.andback.core.domain.User;

/**
 * UserService
 * UserController 에서 받은 데이터를 처리 (인터페이스)
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-04-25
 **/
public interface UserService {
    String insertUser(UserDto userDto);

    String checkUserEmail(String userEmail);

    String checkUserNickname(String userNickname);

    String sendUserEmailNumber(String userEmail);

    String login(LoginRequestDto loginRequestDto);

    String findUserPassword(FindUserPasswordRequestDto findUserPasswordRequestDto);

    void deleteUser(User user);

    String checkUserPassword(User user, String userPassword);

    String updateUser(User user, UpdateUserRequestDto updateUserRequestDto);
}
