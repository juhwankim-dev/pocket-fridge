package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;


/**
 * RecipeProcessResponseDto
 * 레시피 과정정보 전달 DTO
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-02
 * 마지막 수정일 2022-05-02
 **/

@Data
@Builder
public class RecipeProcessResponseDto {

    
    // 레시피 순서
    private int recipeProcessSequence;

    // 레시피 설명
    private String recipeProcessDescription;

    // 이미지 URL
    private String recipeProcessImage;


}
