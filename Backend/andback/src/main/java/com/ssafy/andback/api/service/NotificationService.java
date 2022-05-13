package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.response.NotificationResponseDto;
import com.ssafy.andback.core.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface NotificationService {

    List<NotificationResponseDto> findAllbyUser(User user);

}
