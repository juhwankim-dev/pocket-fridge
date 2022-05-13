package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.request.FindUserPasswordRequestDto;
import com.ssafy.andback.api.dto.request.LoginRequestDto;
import com.ssafy.andback.api.dto.request.UpdateUserRequestDto;
import com.ssafy.andback.config.jwt.JwtAuthenticationProvider;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.core.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserServiceImpl
 * UserService 구체화
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-04-25
 **/

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final UserRefrigeratorRepository userRefrigeratorRepository;
    private final FoodIngredientRepository foodIngredientRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    private final PasswordEncoder passwordEncoder; // WebSecurityConfig.java 에서 Bean 설정
    private final MailService mailService;


    // JWT 토큰 발급받는 객체
    private final JwtAuthenticationProvider jwtAuthenticationProvider;


    @DisplayName("패스워드 암호화")
    public String passwordEncode(String userPassword) {
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

        if (userDto.getUserEmail().equals(""))   // @Email 은 공백을 검사하지 않으므로 조건 추가
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
                .roles(Collections.singletonList("USER"))  // 최초 가입시 USER로 설정
                .build();

        User saveUser = userRepository.save(user);
        // 기본 냉장고 생성 부분 삭제

        return "success";
    }

    // 이메일 중복 검사
    @Override
    public String checkUserEmail(String userEmail) {
        if (userEmail.equals("") || userRepository.existsByUserEmail(userEmail))
            return "fail";
        return "success";
    }

    // 닉네임 중복 검사
    @Override
    public String checkUserNickname(String userNickname) {
        if (userNickname.equals("") || userRepository.existsByUserNickname(userNickname))
            return "fail";
        return "success";
    }


    // 로그인
    @Override
    public String login(LoginRequestDto loginRequestDto) {
        Optional<User> user = userRepository.findByUserEmail(loginRequestDto.getUserEmail());
        if (!user.isPresent()) {
            return "fail";
        }
        // 암호화된 비밀번호 비교
        if (!passwordEncoder.matches(loginRequestDto.getUserPassword(), user.get().getUserPassword())) {
            return "fail";
        }

        // 토큰 발급
        String token = jwtAuthenticationProvider.createToken(user.get().getUserEmail(), user.get().getRoles());

        return token;
    }

    // 비밀번호 찾기 (비밀번호 변경)
    @Override
    @Transactional(readOnly = false) // save 없이 자동으로 업데이트
    public String findUserPassword(FindUserPasswordRequestDto findUserPasswordRequestDto) {

        String userEmail = findUserPasswordRequestDto.getUserEmail();
        Optional<User> user = userRepository.findByUserEmail(userEmail);

        if (!user.isPresent())   // 이메일에 해당하는 유저가 없으면 fail
            return "fail";

        // 다른 이름이 들어왔다면 fail
        if (!user.get().getUserName().equals(findUserPasswordRequestDto.getUserName()))
            return "fail";


        String userPassword = MailService.getRamdomNumber(10);
        mailService.sendNewUserPassword(userEmail, userPassword);
        user.get().setUserPassword(passwordEncoder.encode(userPassword));

        return "success";
    }

    // 회원 탈퇴
    @Override
    public void deleteUser(User user) {
        // 현재 유저의 모든 Refrigerator 삭제
        List<UserRefrigerator> list;
        list = userRefrigeratorRepository.findUserRefrigeratorByUser(user); // 유저가 가진 모든 유저냉장고 가져오기
        // 연관관계 매핑 순서대로 삭제 (FoodIngredient => Refrigerator의 자식, UserRefrigerator => User의 자식 이므로 FoodIngredient와 UserRefrigerator를 먼저 지운다)
        for (UserRefrigerator userRefrigerator : list) {
            // 해당 냉장고가 가진 식재료 모두 삭제
            foodIngredientRepository.deleteFoodIngredientsByRefrigerator(userRefrigerator.getRefrigerator());
            // 현재 유저의 모든 userRefrigerator 삭제
            userRefrigeratorRepository.deleteUserRefrigeratorByRefrigerator(userRefrigerator.getRefrigerator());
            // 해당 냉장고 id 값을 가진 냉장고 삭제
            refrigeratorRepository.deleteRefrigeratorByRefrigeratorId(userRefrigerator.getUserRefrigeratorId());
        }

        // 현재 유저의 레시피 좋아요 삭제
        recipeLikeRepository.deleteRecipeLikeByUser(user);

        // 현재 유저 삭제
        userRepository.deleteUserByUserId(user.getUserId());
    }

    // 회원 정보 수정 전 비밀번호 확인
    @Override
    public String checkUserPassword(User user, String userPassword) {
        // 암호화된 비밀번호 비교
        if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
            return "fail";
        }
        return "success";
    }

    // 회원 정보 수정
    @Override
    @Transactional(readOnly = false) // save 없이 자동으로 업데이트
    public String updateUser(User user, UpdateUserRequestDto updateUserRequestDto) {

        // 닉네임 중복검사
        if (!user.getUserNickname().equals(updateUserRequestDto.getUserNickname()) &&
                userRepository.existsByUserNickname(updateUserRequestDto.getUserNickname()))
            return "fail";
        // 공백이면 원래 데이터 그대로, 변경되었으면 변경
        if (!updateUserRequestDto.getUserNickname().equals("") &&
                !updateUserRequestDto.getUserNickname().equals(user.getUserNickname()))
            user.setUserNickname(updateUserRequestDto.getUserNickname());
        if (!updateUserRequestDto.getUserPassword().equals("") &&
                !passwordEncoder.matches(updateUserRequestDto.getUserPassword(), user.getUserPassword()))
            user.setUserPassword(passwordEncode(updateUserRequestDto.getUserPassword()));
        if (!updateUserRequestDto.getUserPicture().equals("") &&
                !updateUserRequestDto.getUserPicture().equals(user.getUserPicture()))
            user.setUserPicture(updateUserRequestDto.getUserPicture());
        userRepository.save(user);
        return "success";
    }


}
