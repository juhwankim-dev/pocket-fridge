package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.request.InsertFoodIngredientReqDto;
import com.ssafy.andback.api.dto.request.UpdateFoodIngredientReqDto;
import com.ssafy.andback.api.dto.response.FoodIngredientResDto;

import java.util.List;

public interface FoodIngredientService {
    String saveFoodIngredient(InsertFoodIngredientReqDto insertFoodIngredientReqDto);

    String updateFoodIngredient(Long foodIngredientId, UpdateFoodIngredientReqDto updateFoodIngredientReqDto);

    String deleteFoodIngredient(Long foodIngredientId);

    List<FoodIngredientResDto> findAllByRefrigeratorId(Long refrigeratorId);
}
