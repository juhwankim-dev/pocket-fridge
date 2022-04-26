package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class RefrigeratorResponseDto {

    private Long refrigeratorId;

    private String refrigeratorName;

    @Builder
    public RefrigeratorResponseDto(Long refrigeratorId, String refrigeratorName) {
        this.refrigeratorId = refrigeratorId;
        this.refrigeratorName = refrigeratorName;
    }
}
