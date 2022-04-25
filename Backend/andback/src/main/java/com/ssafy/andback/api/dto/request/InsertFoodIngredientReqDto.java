/**
 * FoodIngredientDto
 * 식재료 등록 화면으로 부터 넘어오는 식재료 정보를 가진 dto
 *
 * @author 문관필
 * @version 1.0.0
 * 생성일 2022/04/19
 * 마지막 수정일 2022/04/19
 **/
package com.ssafy.andback.api.dto.request;

import com.ssafy.andback.api.constant.WayStatus;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class InsertFoodIngredientReqDto {

    // 식재료 이름
    @ApiParam(value = "식재료 이름", required = true)
    @NotNull
    private String foodIngredientName;

    // 유통기한
    @ApiParam(value = "식재료 유통기한", required = true)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientExp;

    // 구입일
    @ApiParam(value = "식재료 구입일", required = true)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientDate;

    // 보관방법
    @ApiParam(value = "식재료 보관방법", required = true)
    @NotNull
    private WayStatus foodIngredientWay;

    // 냉장고 아이디
    @ApiParam(value = "냉장고 아이디", required = true)
    @NotNull
    private Long refrigeratorId;
}
