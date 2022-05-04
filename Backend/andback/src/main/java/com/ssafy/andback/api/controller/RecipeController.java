package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.dto.response.LackRecipeIngredientResponseDto;
import com.ssafy.andback.api.dto.response.ListResponseDto;
import com.ssafy.andback.api.dto.response.RecipeProcessResponseDto;
import com.ssafy.andback.api.dto.response.RecipeResponseDto;
import com.ssafy.andback.api.service.RecipeIngredientService;
import com.ssafy.andback.api.service.RecipeService;
import com.ssafy.andback.api.service.UserRefrigeratorService;
import com.ssafy.andback.core.domain.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * RecipeController
 * 레시피 컨트롤러
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-02
 * 마지막 수정일 2022-05-02
 **/

@Api(tags = {"04. 레시피 API"})
@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeIngredientService recipeIngredientService;
    private final UserRefrigeratorService userRefrigeratorService;

    //모든 레시피 출력
    @ApiOperation(value = "모든 레시피 조회", notes = "모든 레시피 리스트를 보여준다")
    @GetMapping
    ResponseEntity<ListResponseDto<RecipeResponseDto>> findAllRecipe(){

        List<RecipeResponseDto> response = recipeService.findAllRecipe();

        return ResponseEntity.ok(new ListResponseDto<RecipeResponseDto>(200, "success", response));
    }


    @ApiOperation(value = "레시피 과정 정보 조회", notes = "레시피 Id로 레시피 과정을 조회한다")
    @GetMapping("/{recipeId}")
    ResponseEntity<ListResponseDto<RecipeProcessResponseDto>> findAllRecipeProcessByRecipeId(@PathVariable(value = "recipeId", required = true) Long recipeId){

        List<RecipeProcessResponseDto> response = recipeService.findRecipeProcessByRecipeId(recipeId);

        return ResponseEntity.ok(new ListResponseDto<RecipeProcessResponseDto>(200, "success", response));
    }

    @ApiOperation(value = "부족한 레시피 재료 조회", notes = "레시피 재료 중 부족한 재료를 보여준다.")
    @GetMapping("/lack/{recipeId}")
    ResponseEntity<ListResponseDto<LackRecipeIngredientResponseDto>> findLackRecipeIngredients(@ApiIgnore Authentication authentication, @PathVariable(value = "recipeId", required = true) Long recipeId) {

        User user = (User) authentication.getPrincipal();

        // 레시피 식재료 리스트
        List<RecipeIngredient> recipeIngredientList = recipeIngredientService.findRecipeIngredientsByRecipeId(recipeId);

        // 유저 냉장고 리스트
        List<UserRefrigerator> refrigeratorList = userRefrigeratorService.findUserRefrigeratorsByUser(user);

        // 유저 냉장고의 식재료 리스트
        List<FoodIngredient> foodIngredientList = recipeIngredientService.findAllFoodIngredientByUserRefrigerators(refrigeratorList);

        // 레시피 식재료 리스트의 재료이름들과 유저의 모든 냉장고의 식재료 리스트의 서브카테고리 이름들을 비교
        List<LackRecipeIngredientResponseDto> response = recipeService.findLackRecipeIngredient(recipeIngredientList, foodIngredientList);

        return ResponseEntity.ok(new ListResponseDto<LackRecipeIngredientResponseDto>(200, "success", response));
    }

}
