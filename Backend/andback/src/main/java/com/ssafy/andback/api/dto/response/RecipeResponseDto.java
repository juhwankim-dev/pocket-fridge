package com.ssafy.andback.api.dto.response;

import com.ssafy.andback.api.constant.RecipeType;
import com.ssafy.andback.core.domain.RecipeLike;
import lombok.Builder;
import lombok.Data;


/**
 * RecipeResponseDto
 * 레시피 전달 DTO
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-02
 * 마지막 수정일 2022-05-02
 **/

@Data
public class RecipeResponseDto {

    // 레시피 아이디
    private Long recipeId;

    // 요리 이름
    private String recipeFoodName;

    // 요리 재료 요약
    private String recipeAllIngredient;

    // 내용
    private String recipeContent;

    // 이미지
    private String recipeImage;

    // 음식 종류
    private RecipeType recipeType;

    // 음식 조리 시간 분
    private String recipeTime;

    // 음식 인분
    private String recipeServing;

    // 좋아요
    private boolean recipeLike;


    @Builder
    public RecipeResponseDto(Long recipeId, String recipeFoodName, String recipeAllIngredient, String recipeContent, String recipeImage, RecipeType recipeType, int recipeTime, int recipeServing, boolean recipeLike) {
        this.recipeId = recipeId;
        this.recipeFoodName = recipeFoodName;
        this.recipeAllIngredient = recipeAllIngredient;
        this.recipeContent = recipeContent;
        this.recipeImage = recipeImage;
        this.recipeType = recipeType;
        this.recipeTime = String.format("%d 분", recipeTime);
        this.recipeServing = String.format("%d 인분", recipeServing);
        this.recipeLike = recipeLike;
    }
}
