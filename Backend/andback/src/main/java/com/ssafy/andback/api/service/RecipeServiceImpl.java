package com.ssafy.andback.api.service;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.response.RecipeIngredientResponseDto;
import com.ssafy.andback.api.dto.response.RecipeProcessResponseDto;
import com.ssafy.andback.api.dto.response.RecipeResponseDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.core.domain.Recipe;
import com.ssafy.andback.core.domain.RecipeIngredient;
import com.ssafy.andback.core.domain.RecipeProcess;
import com.ssafy.andback.core.repository.RecipeIngredientRepository;
import com.ssafy.andback.core.repository.RecipeProcessRepository;
import com.ssafy.andback.core.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * RecipeServiceImpl
 * 레시피 서비스 구현체
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-02
 * 마지막 수정일 2022-05-02
 **/

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    //@RequiredArgsConstructor 자동 주입
    private final RecipeRepository recipeRepository;
    private final RecipeProcessRepository recipeProcessRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    public List<RecipeResponseDto> findAllRecipe() {

        List<Recipe> recipeList = recipeRepository.findAll();

        List<RecipeResponseDto> result = new ArrayList<>();

        for (Recipe temp : recipeList) {
            result.add(RecipeResponseDto.builder()
                    .recipeId(temp.getRecipeId())
                    .recipeAllIngredient(temp.getRecipeAllIngredient())
                    .recipeContent(temp.getRecipeContent())
                    .recipeImage(temp.getRecipeImage())
                    .recipeServing(temp.getRecipeServing())
                    .recipeType(temp.getRecipeType())
                    .recipeFoodName(temp.getRecipeFoodName())
                    .recipeTime(temp.getRecipeTime())
                    .build());
        }

        return result;
    }

    @Override
    public List<RecipeProcessResponseDto> findRecipeProcessByRecipeId(Long recipeId) {

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        recipe.orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        Optional<List<RecipeProcess>> recipeProcessList = recipeProcessRepository.findAllByRecipe(recipe.get());

        if (recipeProcessList.get().size() == 0) {
            throw new CustomException(ErrorCode.RECIPE_PROCESS_NOT_FOUND);
        }

        List<RecipeProcessResponseDto> result = new ArrayList<>();


        for (RecipeProcess temp : recipeProcessList.get()) {
            result.add(RecipeProcessResponseDto.builder()
                    .recipeProcessImage(temp.getRecipeProcessImage())
                    .recipeProcessSequence(temp.getRecipeProcessSequence())
                    .recipeProcessDescription(temp.getRecipeProcessDescription())
                    .build());
        }


        return result;
    }

    @Override
    public List<RecipeIngredientResponseDto> findRecipeIngredientByRecipeId(Long recipeId) {

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        recipe.orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        Optional<List<RecipeIngredient>> recipeIngredients = recipeIngredientRepository.findAllByRecipe(recipe);

        if (recipeIngredients.get().size() == 0) {
            throw new CustomException(ErrorCode.RECIPE_INGREDIENT_NOT_FOUND);
        }

        List<RecipeIngredientResponseDto> result = new ArrayList<>();

        for (RecipeIngredient temp : recipeIngredients.get()) {
            result.add(RecipeIngredientResponseDto.builder()
                    .recipeIngredientName(temp.getRecipeIngredientName())
                    .recipeIngredientCount(temp.getRecipeIngredientCount())
                    .build());
        }

        return result;
    }
}
