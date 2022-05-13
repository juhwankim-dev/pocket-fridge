package com.ssafy.andback.api.service;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.constant.SocialLoginType;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.config.auth.GoogleOauth;
import com.ssafy.andback.config.jwt.JwtAuthenticationProvider;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.Token;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.domain.auth.GoogleOAuthToken;
import com.ssafy.andback.core.domain.auth.GoogleUser;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRefrigeratorRepository;
import com.ssafy.andback.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * OAuthService
 * 소셜 로그인 서비스
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-10
 * 마지막 수정일 2022-05-10
 **/


@Service
@RequiredArgsConstructor
public class OAuthService {
    private final GoogleOauth googleOauth;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;


    public String request(SocialLoginType socialLoginType) throws Exception {

        String redirectURL = "";


        switch (socialLoginType) {
            case GOOGLE: {
                //각 소셜 로그인을 요청하면 소셜로그인 페이지로 리다이렉트 해주는 프로세스
                redirectURL = googleOauth.getOauthRedirectURL();
            }
            break;
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다");
            }
        }


        // 문제가 발생
//        response.sendRedirect(redirectURL);

        return redirectURL;
    }

    public String oAuthLogin(SocialLoginType socialLoginType, String code) throws IOException {

        String Token = "fail";

        switch (socialLoginType) {
            case GOOGLE: {
                //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
                ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToken(code);
                //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
                GoogleOAuthToken oAuthToken = googleOauth.getAccessToken(accessTokenResponse);

                //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
                ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(oAuthToken);
                //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
                GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);

                String user_id = googleUser.getEmail();

                //유저 이메일을 가지고 온다
                Optional<User> user = userRepository.findByUserEmail(user_id);

                User result = null;

                if (user.isEmpty()) {
                    result = User.builder()
                            .userNickname("google_" + googleUser.getId())
                            .userLoginType(true)
                            .userName(googleUser.getName())
                            .userPicture(googleUser.getPicture())
                            .userPassword(passwordEncoder.encode(getRamdomNumber(12)))
                            .userEmail(googleUser.getEmail())
                            .roles(Collections.singletonList("USER"))  // 최초 가입시 USER로 설정
                            .build();
                    // 기본 냉장고 생성 부분 삭제
                    userRepository.save(result);
                } else {
                    if (!user.get().getUserLoginType()){ // 유저 로그인 타입이 false 이면 소셜로그인이 아니다
                        throw new CustomException(ErrorCode.EMAIL_DUPLICATION);
                    }

                    // true이면 반환
                    result = user.get();
                }

                Token = jwtAuthenticationProvider.createToken(result.getUserEmail(), result.getRoles());
            }
            break;
            default: {
                throw new IllegalArgumentException("알 수 없는 소셜 로그인 형식입니다.");
            }
        }
        return Token;
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

}
