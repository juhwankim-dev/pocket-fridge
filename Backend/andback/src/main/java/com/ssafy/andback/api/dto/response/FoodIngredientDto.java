/**
* FoodIngredientDto
* 식재료 등록 화면으로 부터 넘어오는 식재료 정보를 가진 dto
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/04/19
* 마지막 수정일 2022/04/19
**/
package com.ssafy.andback.api.dto.response;

import com.ssafy.andback.api.constant.WayStatus;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter @Setter
public class FoodIngredientDto {

    // 식재료 이름
    @ApiModelProperty(example = "오이")
    @NotNull
    private String foodIngredientName;

    // 유통기한
    @ApiModelProperty(example = "2022-04-11")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientExp;

    // 식재로 카테고리
    @ApiModelProperty(example = "채소")
    @NotNull
    private String foodIngredientCategory;

    @ApiModelProperty(example = "1")
    // 식재료 수량
    @NotNull
    private int foodIngredientCount;

    // 구입일
    @ApiModelProperty(example = "2022-03-25")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientDate;

    @ApiModelProperty(example = "냉장")
    // 보관방법
    private WayStatus foodIngredientWay;


}
