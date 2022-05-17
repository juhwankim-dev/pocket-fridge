package com.ssafy.andback.api.service;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * FirebaseCloudMessageService
 * 파이어베이스 메세지 서비스
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-03
 * 마지막 수정일 2022-05-03
 **/

@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {

    private final String API_URL = "https://fcm.googleapis.com/v1/projects/andbackproject/messages:send";
    private final ObjectMapper objectMapper;


    public void sendMessageTo(String targetToken, String title, String body) {
        Message message = makeMessage(targetToken, title, body);

        FirebaseMessaging.getInstance().sendAsync(message);

    }

    private Message makeMessage(String targetToken, String title, String body) {


        Message message = Message.builder()
                .putData("action", "notification")
                .setToken(targetToken)
                .setNotification(
                        Notification.builder()
                                .setBody(body)
                                .setTitle(title)
                                .setImage(null)
                                .build()
                )
                .build();

        return message;
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
