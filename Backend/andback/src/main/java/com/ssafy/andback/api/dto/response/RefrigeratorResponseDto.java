package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefrigeratorResponseDto {

    private Long refrigeratorId; // 냉장고 id

    private String refrigeratorName; // 냉장고 이름

    private boolean refrigeratorOwner; // 냉장고 주인 여부

    @Builder
    public RefrigeratorResponseDto(Long refrigeratorId, String refrigeratorName, boolean refrigeratorOwner) {
        this.refrigeratorId = refrigeratorId;
        this.refrigeratorName = refrigeratorName;
        this.refrigeratorOwner = refrigeratorOwner;
    }
}
