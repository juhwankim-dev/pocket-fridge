package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
public class MainCategoryResponseDto {

    private Long mainCategory;

    private String mainCategoryName;

    @Builder
    public MainCategoryResponseDto(Long mainCategory, String mainCategoryName) {
        this.mainCategory = mainCategory;
        this.mainCategoryName = mainCategoryName;
    }
}
