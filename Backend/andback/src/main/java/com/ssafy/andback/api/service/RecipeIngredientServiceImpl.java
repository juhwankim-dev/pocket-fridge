package com.ssafy.andback.api.service;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.core.domain.*;
import com.ssafy.andback.core.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeRepository recipeRepository;

    @Override
    public List<RecipeIngredient> findRecipeIngredientsByRecipeId(Long recipeId) {

        Optional<Recipe> recipe = recipeRepository.findByRecipeId(recipeId);
        recipe.orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        // 레시피의 필요 식재료 리스트
        List<RecipeIngredient> recipeIngredientList = new ArrayList<>();
        for (int i = 0; i < recipe.get().getRecipeIngredientList().size(); i++) {
            recipeIngredientList.add(RecipeIngredient.builder()
                    .recipeIngredientId(recipe.get().getRecipeIngredientList().get(i).getRecipeIngredientId())
                    .recipeIngredientName(recipe.get().getRecipeIngredientList().get(i).getRecipeIngredientName())
                    .subCategory(recipe.get().getRecipeIngredientList().get(i).getSubCategory())
                    .build());
        }

        return recipeIngredientList;
    }

    @Override
    public List<FoodIngredient> findAllFoodIngredientByUserRefrigerators(List<UserRefrigerator> userRefrigerators) {

        // 전체 유저 냉장고의 식재료 리스트
        List<FoodIngredient> userFoodIngredient = new ArrayList<>();

        for (UserRefrigerator userRefrigerator : userRefrigerators) {
            Refrigerator ref = userRefrigerator.getRefrigerator();

            for (int i = 0; i < ref.getFoodIngredientList().size(); i++) {
                userFoodIngredient.add(FoodIngredient.builder()
                        .foodIngredientName(ref.getFoodIngredientList().get(i).getFoodIngredientName())
                        .foodIngredientDate(ref.getFoodIngredientList().get(i).getFoodIngredientDate())
                        .foodIngredientExp(ref.getFoodIngredientList().get(i).getFoodIngredientExp())
                        .foodIngredientWay(ref.getFoodIngredientList().get(i).getFoodIngredientWay())
                        .subCategory(ref.getFoodIngredientList().get(i).getSubCategory())
                        .build());
            }
        }

        return userFoodIngredient;
    }
}
