package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
public class SubCategoryResponseDto {

    private Long subCategory;

    private String subCategoryName;

    private Long mainCategoryId;


    @Builder
    public SubCategoryResponseDto(Long subCategory, String subCategoryName, Long mainCategoryId) {
        this.subCategory = subCategory;
        this.subCategoryName = subCategoryName;
        this.mainCategoryId = mainCategoryId;
    }
}
