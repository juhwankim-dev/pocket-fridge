/**
 * FoodIngredientDto
 * 식재료 등록 화면으로 부터 넘어오는 식재료 정보를 가진 dto
 *
 * @author 문관필
 * @version 1.0.0
 * 생성일 2022/04/19
 * 마지막 수정일 2022/04/19
 **/
package com.ssafy.andback.api.dto;

import com.ssafy.andback.api.constant.WayStatus;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class FoodIngredientDto {

    // 식재료 이름
    @NotNull
    private String foodIngredientName;

    // 유통기한
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientExp;

    // 식재로 카테고리
    @NotNull
    private String foodIngredientCategory;

    // 식재료 수량
    @NotNull
    private int foodIngredientCount;

    // 구입일
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientDate;

    // 보관방법
    @NotNull
    private WayStatus foodIngredientWay;
}
