package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.request.InsertFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.request.UpdateFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.response.FoodIngredientResponseDto;

import java.util.List;

public interface FoodIngredientService {
    String saveFoodIngredient(InsertFoodIngredientRequestDto insertFoodIngredientReqDto);

    String updateFoodIngredient(Long foodIngredientId, UpdateFoodIngredientRequestDto updateFoodIngredientReqDto);

    String deleteFoodIngredient(Long foodIngredientId);

    List<FoodIngredientResponseDto> findAllByRefrigeratorId(Long refrigeratorId);
}
