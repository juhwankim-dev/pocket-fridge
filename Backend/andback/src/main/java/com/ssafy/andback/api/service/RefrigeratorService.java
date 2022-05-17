package com.ssafy.andback.api.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.ssafy.andback.api.dto.request.InsertRefrigeratorRequestDto;
import com.ssafy.andback.api.dto.request.InsertShareMemberRequestDto;
import com.ssafy.andback.api.dto.response.RefrigeratorResponseDto;
import com.ssafy.andback.api.dto.response.RefrigeratorShareUserResponseDto;
import com.ssafy.andback.core.domain.User;

import java.io.IOException;
import java.util.List;

/**
 * RefrigeratorService
 * 냉장고 서비스 인터페이스
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-25
 * 마지막 수정일 2022-04-25
 **/

public interface RefrigeratorService {

    public String insertRefrigerator(User user, String refrigeratorName);

    public List<RefrigeratorResponseDto> findAllRefrigeratorByUser(User user);

    public String createShareGroup(User user, InsertShareMemberRequestDto insertShareMemberRequestDto);

    public String updateRefrigerator(User user, Long refrigeratorId, String refrigeratorName);

    public String deleteRefrigerator(User user, Long refrigeratorId);

    List<RefrigeratorShareUserResponseDto> shareUserList(User user, Long refrigeratorId);

    public boolean deleteUser(User user, Long refrigeratorId, String userEmail) throws IOException;

}
