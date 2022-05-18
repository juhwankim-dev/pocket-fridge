package com.ssafy.andback.api.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class RecipeIngredientRequireCountDto {

    // 레시피 아이디
    private Long recipeId;

    private Long count;

    @QueryProjection
    public RecipeIngredientRequireCountDto(Long recipeId, Long count) {
        this.recipeId = recipeId;
        this.count = count;
    }
}
