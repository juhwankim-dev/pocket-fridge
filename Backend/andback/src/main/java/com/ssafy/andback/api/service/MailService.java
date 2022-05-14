package com.ssafy.andback.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    // 이메일 인증번호 전송
    @Async
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


    // 비밀번호 찾기에서 새 비밀번호 전송
    @Async
    public String sendNewUserPassword(String userEmail, String passWord) {
        // 이메일 인증번호 생성


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
        simpleMessage.setSubject("[임시 비밀번호 변경 안내] 포켓프리지 입니다.");

        // 메일 내용
        simpleMessage.setText("임시 비밀번호는\n\n" + passWord + "\n\n입니다.");

        // 메일 발송
        javaMailSender.send(simpleMessage);

        return passWord;
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
