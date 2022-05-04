package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;


/**
*
* RecipeIngredientResponseDto
* 레시피 재료 응답 DTO
*
* @author hoony
* @version 1.0.0
* 생성일 2022-05-04
* 마지막 수정일 2022-05-04
**/

@Builder
@Data
public class RecipeIngredientResponseDto {

    // 레시피 재료 이름
    private String recipeIngredientName;

    // 레시피 재료 수량 (ex 1 큰술)
    private String recipeIngredientCount;

}
