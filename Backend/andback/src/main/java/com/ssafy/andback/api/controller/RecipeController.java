package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.dto.response.ListResponseDto;
import com.ssafy.andback.api.dto.response.RecipeProcessResponseDto;
import com.ssafy.andback.api.dto.response.RecipeResponseDto;
import com.ssafy.andback.api.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
