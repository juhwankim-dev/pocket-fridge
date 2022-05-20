package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;


@Data
public class SubCategoryResponseDto {

    private Long subCategoryId;

    private String subCategoryName;

    private Long mainCategoryId;

    private String subCategoryUrl;


    @Builder
    public SubCategoryResponseDto(Long subCategoryId, String subCategoryName, Long mainCategoryId, String subCategoryUrl) {
        this.subCategoryId = subCategoryId;
        this.subCategoryName = subCategoryName;
        this.mainCategoryId = mainCategoryId;
        this.subCategoryUrl = subCategoryUrl;
    }
}
