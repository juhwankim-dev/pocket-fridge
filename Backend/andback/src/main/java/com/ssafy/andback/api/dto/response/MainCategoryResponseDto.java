package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
public class MainCategoryResponseDto {

    private Long mainCategoryId;

    private String mainCategoryName;

    @Builder
    public MainCategoryResponseDto(Long mainCategoryId, String mainCategoryName) {
        this.mainCategoryId = mainCategoryId;
        this.mainCategoryName = mainCategoryName;
    }
}
