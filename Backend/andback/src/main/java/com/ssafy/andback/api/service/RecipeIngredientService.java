package com.ssafy.andback.api.service;

import com.ssafy.andback.core.domain.FoodIngredient;
import com.ssafy.andback.core.domain.RecipeIngredient;
import com.ssafy.andback.core.domain.UserRefrigerator;

import java.util.List;

public interface RecipeIngredientService {

    List<RecipeIngredient> findRecipeIngredientsByRecipeId(Long recipeId);
    List<FoodIngredient> findAllFoodIngredientByUserRefrigerators(List<UserRefrigerator> userRefrigerators);
}
