package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.core.domain.user.User;
import com.ssafy.andback.core.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * UserServiceImpl
 * UserService 구체화
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-04-19
 **/

@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // WebSecurityConfiguration.java 에서 Bean 설정

    @DisplayName("패스워드 암호화")
    public String passwordEncode(String userPassword){
        String encodedPassword = passwordEncoder.encode(userPassword);

        assertAll(
                () -> assertNotEquals(userPassword, encodedPassword),
                () -> assertTrue(passwordEncoder.matches(userPassword, encodedPassword))
        );
        return encodedPassword;
    }

    // 회원가입
    @Override
    public String insertUser(UserDto userDto) {
        if(userDto.getUserEmail().equals("") || userDto.getUserName().equals("") ||
        userDto.getUserNickname().equals("") || userDto.getUserPassword().equals(""))
            return "fail";
        String userPassword = passwordEncode(userDto.getUserPassword());
        userDto.setUserPassword(userPassword);
        User user = User.builder()
                .userEmail(userDto.getUserEmail())
                .userName(userDto.getUserName())
                .userNickname(userDto.getUserNickname())
                .userPassword(userDto.getUserPassword())
                .userPicture(userDto.getUserPicture())
                .userLoginType(userDto.getUserLoginType())
                .build();
        userRepository.save(user);
        return "success";
    }
}
