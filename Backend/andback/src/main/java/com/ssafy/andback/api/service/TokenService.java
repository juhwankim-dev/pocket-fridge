package com.ssafy.andback.api.service;


import com.ssafy.andback.api.dto.request.TokenRequestDto;
import com.ssafy.andback.core.domain.User;

import java.io.IOException;

/**
*
* TokenService
* 토큰 서비스 인터페이스
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-29
* 마지막 수정일 2022-04-29
**/

public interface TokenService {

    String insertToken(User user, TokenRequestDto tokenDto);

    String sendMessage(String userEmail, Long refrigeratorId) throws IOException;

}
