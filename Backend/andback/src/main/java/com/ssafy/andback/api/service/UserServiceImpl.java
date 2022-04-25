package com.ssafy.andback.api.service;

import org.springframework.stereotype.Service;
import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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

        if(userDto.getUserEmail().equals(""))   // @Email 은 공백을 검사하지 않으므로 조건 추가
            return "fail";

        String userPassword = passwordEncode(userDto.getUserPassword());
        userDto.setUserPassword(userPassword);
        User user = User.builder()
                .userEmail(userDto.getUserEmail())
                .userName(userDto.getUserName())
                .userNickname(userDto.getUserNickname())
                .userPassword(userDto.getUserPassword())
                .userPicture(userDto.getUserPicture())
                .userLoginType(false)
                .build();
        userRepository.save(user);
        return "success";
    }

    // 이메일 중복 검사
    @Override
    public String checkUserEmail(String userEmail) {

        if(userEmail.equals("") || userRepository.existsByUserEmail(userEmail))
            return "fail";

        return "success";
    }
}
