package com.ssafy.andback.api.dto.response;

import com.ssafy.andback.api.constant.WayStatus;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class FoodIngredientResponseDto {

    // 식재료 이름
    @ApiParam(value = "식재료 이름", required = true)
    private String foodIngredientName;

    // 유통기한
    @ApiParam(value = "식재료 유통기한", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientExp;

    // 구입일
    @ApiParam(value = "식재료 구입일", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientDate;

    // 보관방법
    @ApiParam(value = "식재료 보관방법", required = true)
    private WayStatus foodIngredientWay;

    // 냉장고 아이디
    @ApiParam(value = "냉장고 아이디", required = true)
    private Long refrigeratorId;

    @Builder
    public FoodIngredientResponseDto(String foodIngredientName, LocalDate foodIngredientExp, LocalDate foodIngredientDate, WayStatus foodIngredientWay, Long refrigeratorId) {
        this.foodIngredientName = foodIngredientName;
        this.foodIngredientExp = foodIngredientExp;
        this.foodIngredientDate = foodIngredientDate;
        this.foodIngredientWay = foodIngredientWay;
        this.refrigeratorId = refrigeratorId;
    }
}
