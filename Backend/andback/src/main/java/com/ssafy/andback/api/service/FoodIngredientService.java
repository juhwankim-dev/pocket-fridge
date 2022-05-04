package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.request.InsertFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.request.UpdateFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.response.FoodIngredientResponseDto;
import com.ssafy.andback.api.dto.response.MainCategoryResponseDto;
import com.ssafy.andback.api.dto.response.SubCategoryResponseDto;

import java.util.List;

public interface FoodIngredientService {
    String saveFoodIngredient(InsertFoodIngredientRequestDto insertFoodIngredientReqDto);

    String updateFoodIngredient(UpdateFoodIngredientRequestDto updateFoodIngredientReqDto);

    String deleteFoodIngredient(Long foodIngredientId);

    List<FoodIngredientResponseDto> findAllByRefrigeratorId(Long refrigeratorId);

    List<MainCategoryResponseDto> findAllMainCategory();

    List<SubCategoryResponseDto> findAllSubCategory();
}
