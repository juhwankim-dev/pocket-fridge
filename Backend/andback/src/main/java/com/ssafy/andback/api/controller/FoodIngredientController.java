/**
* FoodIngredientController
* 식재료 관련 컨트롤러
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/04/19
* 마지막 수정일 2022/04/19
**/
package com.ssafy.andback.api.controller;

import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.dto.FoodIngredientDto;
import com.ssafy.andback.api.service.FoodIngredientServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "식재료 API", tags = {"FoodIngredient"})
@RestController
@RequestMapping("/foodIngredient")
@RequiredArgsConstructor
public class FoodIngredientController {

    private final FoodIngredientServiceImpl foodIngredientServiceImpl;

    @ApiOperation(value = "식재료 등록", notes = "식재료 정보를 입력받아 DB에 저장한다.")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(
                            name = "foodIngredientName",
                            value = "식재료 이름",
                            required = true
                    ),
                    @ApiImplicitParam(
                            name = "foodIngredientExp",
                            value = "식재료 유통기한",
                            required = true
                    ),
                    @ApiImplicitParam(
                            name = "foodIngredientCategory",
                            value = "식재료 카테고리",
                            required = true
                    ),
                    @ApiImplicitParam(
                            name = "foodIngredientCount",
                            value = "식재료 수량",
                            required = true
                    ),
                    @ApiImplicitParam(
                            name = "foodIngredientDate",
                            value = "식재료 구매일",
                            required = true
                    ),
                    @ApiImplicitParam(
                            name = "foodIngredientWay",
                            value = "식재료 보관방법(냉장, 실온)",
                            required = true
                    )
            }
    )
    @PostMapping("/insert")
    public ResponseEntity<BaseResponseDto> insertFoodIngredient(FoodIngredientDto foodIngredientDto) {
        String foodIngredient = foodIngredientServiceImpl.saveFoodIngredient(foodIngredientDto);

        if (foodIngredient.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "식재료 등록 실패"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "식재료 등록 성공"));
    }
}
