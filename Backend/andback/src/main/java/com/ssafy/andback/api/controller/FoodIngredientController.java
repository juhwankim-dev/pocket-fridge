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

import com.ssafy.andback.api.dto.request.UpdateFoodIngredientReqDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.dto.request.InsertFoodIngredientReqDto;
import com.ssafy.andback.api.service.FoodIngredientServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "식재료 API", tags = {"FoodIngredient"})
@RestController
@RequestMapping("/foodingredient")
@RequiredArgsConstructor
public class FoodIngredientController {

    private final FoodIngredientServiceImpl foodIngredientServiceImpl;

    @ApiOperation(value = "식재료 등록", notes = "식재료 정보를 입력받아 DB에 저장한다.")
    @PostMapping("/insert")
    public ResponseEntity<BaseResponseDto> insertFoodIngredient(InsertFoodIngredientReqDto insertFoodIngredientReqDto) {

        String res = foodIngredientServiceImpl.saveFoodIngredient(insertFoodIngredientReqDto);

        if (res.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "식재료 등록 실패"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "식재료 등록 성공"));
    }

    @ApiOperation(value = "식재료 수정", notes = "식재료 정보를 입력받아 해당하는 식재료의 정보를 DB에 수정한다.")
    @PostMapping("/update/{foodIngredientId}")
    public ResponseEntity<BaseResponseDto> updateFoodIngredient(@PathVariable Long foodIngredientId,
                                                                UpdateFoodIngredientReqDto updateFoodIngredientReqDto) {
        String res = foodIngredientServiceImpl.updateFoodIngredient(foodIngredientId, updateFoodIngredientReqDto);

        if (res.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "식재료 수정 실패"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "식재료 수정 성공"));
    }

    @ApiOperation(value = "식재료 삭제", notes = "해당하는 식재료를 DB에서 삭제한다.")
    @DeleteMapping("/delete/{foodIngredientId}")
    public ResponseEntity<BaseResponseDto> deleteFoodIngredient(@PathVariable Long foodIngredientId) {

        String res = foodIngredientServiceImpl.deleteFoodIngredient(foodIngredientId);

        if (res.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "식재료 삭제 실패"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "식재료 삭제 성공"));
    }
}
