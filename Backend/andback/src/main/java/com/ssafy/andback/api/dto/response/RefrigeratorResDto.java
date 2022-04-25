package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class RefrigeratorResDto {

    private Long refrigeratorId;

    private String refrigeratorName;

    @Builder
    public RefrigeratorResDto(Long refrigeratorId, String refrigeratorName) {
        this.refrigeratorId = refrigeratorId;
        this.refrigeratorName = refrigeratorName;
    }
}
