package com.ssafy.andback.api.service;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.response.LackRecipeIngredientResponseDto;
import com.ssafy.andback.api.dto.response.RecipeIngredientResponseDto;
import com.ssafy.andback.api.dto.response.RecipeProcessResponseDto;
import com.ssafy.andback.api.dto.response.RecipeResponseDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.core.domain.*;
import com.ssafy.andback.core.repository.RecipeIngredientRepository;
import com.ssafy.andback.core.repository.RecipeLikeRepository;
import com.ssafy.andback.core.repository.RecipeProcessRepository;
import com.ssafy.andback.core.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

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
@Transactional
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    //@RequiredArgsConstructor 자동 주입
    private final RecipeRepository recipeRepository;
    private final RecipeProcessRepository recipeProcessRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeLikeRepository recipeLikeRepository;
    private final RestTemplate restTemplate;

    private static final String base_url = "http://k6d206.p.ssafy.io:5000/recommend/";


    @Override
    public List<RecipeResponseDto> findAllRecipe(User user) throws CustomException {

        List<Recipe> recipeList = recipeRepository.findAll();
        List<RecipeLike> recipeLikeList = recipeLikeRepository.findAllByUser(user);
        List<RecipeResponseDto> result = new ArrayList<>();

        Set<Long> set = new HashSet<>();
        for (RecipeLike recipeLike : recipeLikeList) {
            set.add(recipeLike.getRecipe().getRecipeId());
        }

        for (int i = 0; i < recipeList.size(); i++) {
            Recipe temp = recipeList.get(i);

            if (set.contains(temp.getRecipeId())) {
                result.add(RecipeResponseDto.builder()
                        .recipeId(temp.getRecipeId())
                        .recipeAllIngredient(temp.getRecipeAllIngredient())
                        .recipeContent(temp.getRecipeContent())
                        .recipeImage(temp.getRecipeImage())
                        .recipeServing(temp.getRecipeServing())
                        .recipeType(temp.getRecipeType())
                        .recipeFoodName(temp.getRecipeFoodName())
                        .recipeTime(temp.getRecipeTime())
                        .recipeLike(true)
                        .build());
            } else {
                result.add(RecipeResponseDto.builder()
                        .recipeId(temp.getRecipeId())
                        .recipeAllIngredient(temp.getRecipeAllIngredient())
                        .recipeContent(temp.getRecipeContent())
                        .recipeImage(temp.getRecipeImage())
                        .recipeServing(temp.getRecipeServing())
                        .recipeType(temp.getRecipeType())
                        .recipeFoodName(temp.getRecipeFoodName())
                        .recipeTime(temp.getRecipeTime())
                        .recipeLike(false)
                        .build());
            }
        }

        return result;
    }

    @Override
    public List<RecipeProcessResponseDto> findRecipeProcessByRecipeId(Long recipeId) throws CustomException {

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
    @Transactional
    public List<LackRecipeIngredientResponseDto> findLackRecipeIngredient(List<RecipeIngredient> recipeIngredientList, List<FoodIngredient> foodIngredientList) throws CustomException {

        List<LackRecipeIngredientResponseDto> result = new ArrayList<>();
        HashSet<String> recipeIngredientSubCategoryNames = new HashSet<>();
        HashSet<String> foodIngredientSubCategoryNames = new HashSet<>();

        for (int i = 0; i < recipeIngredientList.size(); i++) {
            recipeIngredientSubCategoryNames.add(recipeIngredientList.get(i).getSubCategory().getSubCategoryName());
        }

        for (int i = 0; i < foodIngredientList.size(); i++) {
            foodIngredientSubCategoryNames.add(foodIngredientList.get(i).getSubCategory().getSubCategoryName());
        }

        recipeIngredientSubCategoryNames.removeAll(foodIngredientSubCategoryNames);

        for (String foodName : recipeIngredientSubCategoryNames) {
            result.add(LackRecipeIngredientResponseDto.builder()
                    .RecipeIngredientName(foodName)
                    .build());
        }

        return result;
    }

    @Override
    public ResponseEntity<String> recommendRecipeList(Long userId) throws CustomException {


        String url = base_url + userId;

        // GET 방식으로 요청한다
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);


        if (entity.getStatusCode() == HttpStatus.OK) {
            return entity;
        } else {
            throw new CustomException(ErrorCode.RECOMMEND_REFRIGERATOR_NOT_FOUND);
        }
    }

    @Override
    public List<RecipeIngredientResponseDto> findRecipeIngredientByRecipeId(Long recipeId) throws CustomException {

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
