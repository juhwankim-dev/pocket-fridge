package com.ssafy.andback.core.queryrepository;

import com.ssafy.andback.api.dto.response.RefrigeratorShareUserResponseDto;

import java.util.List;

public interface UserRefrigeratorQueryRepository {

    List<RefrigeratorShareUserResponseDto> shareUserList(Long refrigeratorId);

}
