package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.response.LackRecipeIngredientResponseDto;
import com.ssafy.andback.api.dto.response.RecipeIngredientResponseDto;
import com.ssafy.andback.api.dto.response.RecipeProcessResponseDto;
import com.ssafy.andback.api.dto.response.RecipeResponseDto;
import com.ssafy.andback.core.domain.FoodIngredient;
import com.ssafy.andback.core.domain.RecipeIngredient;

import java.util.List;

/**
 * RecipeService
 * 레시피 서비스 인터페이스
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-02
 * 마지막 수정일 2022-05-02
 **/

public interface RecipeService {

    List<RecipeResponseDto> findAllRecipe();
    List<RecipeProcessResponseDto> findRecipeProcessByRecipeId(Long recipeId);
    List<RecipeIngredientResponseDto> findRecipeIngredientByRecipeId(Long recipeId);
    List<LackRecipeIngredientResponseDto> findLackRecipeIngredient(List<RecipeIngredient> recipeIngredientList, List<FoodIngredient> foodIngredientList);

}
