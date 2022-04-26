package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
public class SubCategoryResponseDto {

    private Long subCategoryId;

    private String subCategoryName;

    private Long mainCategoryId;


    @Builder
    public SubCategoryResponseDto(Long subCategoryId, String subCategoryName, Long mainCategoryId) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.mainCategoryId = mainCategoryId;
    }
}
