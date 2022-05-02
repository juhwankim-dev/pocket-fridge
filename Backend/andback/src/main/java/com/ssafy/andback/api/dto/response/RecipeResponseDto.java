package com.ssafy.andback.api.dto.response;

import com.ssafy.andback.api.constant.RecipeType;
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
@Builder
public class RecipeResponseDto {

    // 레시피 아이디
    private Long recipeId;

    // 요리 이름
    private String recipeFoodName;

    // 요리 요약
    private String recipeFoodSummary;

    // 내용
    private String recipeContent;

    // 이미지
    private String recipeImage;

    // 음식 종류
    private RecipeType recipeType;

}
