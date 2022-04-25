package com.ssafy.andback.api.service;

import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRefrigeratorRepository;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final UserRefrigeratorRepository userRefrigeratorRepository;

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

        User saveUser = userRepository.save(user);
        Refrigerator refrigerator = Refrigerator.builder().refrigeratorName("냉장고").build();
        Refrigerator saveRefrigerator = refrigeratorRepository.save(refrigerator);
        UserRefrigerator userRefrigerator = UserRefrigerator.builder().user(saveUser).refrigerator(saveRefrigerator).build();
        userRefrigeratorRepository.save(userRefrigerator);

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
