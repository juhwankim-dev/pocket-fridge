package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class InviteShareMemberResponseDto {

    // 공유할 냉장고 아이디
    private Long refrigeratorId;

    @Builder
    public InviteShareMemberResponseDto(Long refrigeratorId) {
        this.refrigeratorId = refrigeratorId;
    }
}
