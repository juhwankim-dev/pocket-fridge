package com.ssafy.andback.api.dto.response;

import com.ssafy.andback.api.constant.WayStatus;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * FoodIngredientResponseDto
 * 식재료 ResponseDto
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-03
 * 마지막 수정일 2022-05-03
 **/

@Data
public class FoodIngredientResponseDto {

    //식재료 고유 번호
    @ApiParam(value = "식재료 고유 번호", required = true)
    private Long foodIngredientId;

    // 식재료 이름
    @ApiParam(value = "식재료 이름", required = true)
    private String foodIngredientName;

    // 유통기한
    @ApiParam(value = "식재료 유통기한", required = true)
    private String foodIngredientExp;

    // 구입일
    @ApiParam(value = "식재료 구입일", required = true)
    private String foodIngredientDate;

    // 보관방법
    @ApiParam(value = "식재료 보관방법", required = true)
    private WayStatus foodIngredientWay;

    // 냉장고 아이디
    @ApiParam(value = "냉장고 아이디", required = true)
    private Long refrigeratorId;

    //서브 카테고리 아이디
    @ApiParam(value = "카테고리 아이디", required = true)
    private Long subCategoryId;

    //메인 카테고리 아이디
    @ApiParam(value = "메인 카데고리 아이디", required = true)
    private Long mainCategoryId;


    @Builder
    public FoodIngredientResponseDto(Long foodIngredientId, String foodIngredientName, LocalDate foodIngredientExp, LocalDate foodIngredientDate, WayStatus foodIngredientWay, Long refrigeratorId, Long subCategoryId, Long mainCategoryId) {
        this.foodIngredientId = foodIngredientId;
        this.foodIngredientName = foodIngredientName;
        this.foodIngredientExp = foodIngredientExp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.foodIngredientDate = foodIngredientDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.foodIngredientWay = foodIngredientWay;
        this.refrigeratorId = refrigeratorId;
        this.subCategoryId = subCategoryId;
        this.mainCategoryId = mainCategoryId;
    }
}
