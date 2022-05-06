package com.ssafy.andback.api.dto.response;

import lombok.Builder;
import lombok.Data;

/**
* LackRecipeIngredientResponseDto
* 부족한 재료 전달 Dto
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/05/03
* 마지막 수정일 2022/05/03
**/

@Data
@Builder
public class LackRecipeIngredientResponseDto {

    // 부족한 재료 이름
    private String RecipeIngredientName;

}
