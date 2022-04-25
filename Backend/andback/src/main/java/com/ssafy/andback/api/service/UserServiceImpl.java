package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.request.LoginRequestDto;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRefrigeratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.ssafy.andback.api.dto.UserDto;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
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

    @Autowired
    private PasswordEncoder passwordEncoder; // WebSecurityConfig.java 에서 Bean 설정

    private final JavaMailSender javaMailSender;  // WebSecurityConfig.java 에서 Bean 설정

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

    // 이메일 인증번호 전송
    @Override
    public String sendUserEmailNumber(String userEmail) {
        // 이메일 인증번호 생성
        String tempEmailNumber = getRamdomNumber(10);

        // 수신 대상을 담을 ArrayList 생성
        ArrayList<String> toUserList = new ArrayList<>();

        // 수신 대상 추가
        toUserList.add(userEmail);

        // 수신 대상 개수
        int toUserSize = toUserList.size();

        // SimpleMailMessage (단순 텍스트 구성 메일 메시지 생성할 때 이용)
        SimpleMailMessage simpleMessage = new SimpleMailMessage();

        // 수신자 설정
        simpleMessage.setTo((String[]) toUserList.toArray(new String[toUserSize]));

        // 메일 제목
        simpleMessage.setSubject("[이메일 인증번호 안내] 포켓프리지 입니다.");

        // 메일 내용
        simpleMessage.setText("이메일 인증번호는\n\n" + tempEmailNumber + "\n\n입니다.");

        // 메일 발송
        javaMailSender.send(simpleMessage);

        return tempEmailNumber;
    }

    // 인증번호 생성
    public static String getRamdomNumber(int len) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int idx = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            idx = (int) (charSet.length * Math.random()); // 36 * 생성된 난수를 Int로 추출 (소숫점제거)
            sb.append(charSet[idx]);
        }
        return sb.toString();
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
        return "success";
    }

    // 비밀번호 찾기 (비밀번호 변경)
    @Override
    public String findUserPassword(String userEmail) {
        Optional<User> user = userRepository.findByUserEmail(userEmail);
        if (!user.isPresent()) {
            return "fail";
        }

        String userPassword = sendUserEmailNumber(userEmail);

        user.get().setUserPassword(passwordEncoder.encode(userPassword));
        userRepository.save(user.get());

        return "success";
    }



}
