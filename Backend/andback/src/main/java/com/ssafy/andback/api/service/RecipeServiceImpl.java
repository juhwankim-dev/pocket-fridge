package com.ssafy.andback.api.service;

import com.ssafy.andback.api.dto.response.RecipeResponseDto;
import com.ssafy.andback.core.domain.Recipe;
import com.ssafy.andback.core.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<RecipeResponseDto> findAllRecipe() {

        List<Recipe> recipeList = recipeRepository.findAll();

        List<RecipeResponseDto> result = new ArrayList<>();

        for (Recipe temp : recipeList) {
            result.add(RecipeResponseDto.builder()
                    .recipeImage(temp.getRecipeImage())
                    .recipeType(temp.getRecipeType())
                    .recipeId(temp.getRecipeId())
                    .recipeFoodName(temp.getRecipeFoodName())
                    .recipeContent(temp.getRecipeContent())
                    .recipeFoodSummary(temp.getRecipeFoodSummary())
                    .build());
        }

        return result;
    }
}
