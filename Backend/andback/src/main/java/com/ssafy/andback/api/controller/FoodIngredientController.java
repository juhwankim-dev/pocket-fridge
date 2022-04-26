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

import com.ssafy.andback.api.dto.request.UpdateFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.response.BaseResponseDto;
import com.ssafy.andback.api.dto.request.InsertFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.response.FoodIngredientResponseDto;
import com.ssafy.andback.api.dto.response.ListResponseDto;
import com.ssafy.andback.api.service.FoodIngredientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "식재료 API", tags = {"FoodIngredient"})
@RestController
@RequestMapping("/foodingredient")
@RequiredArgsConstructor
public class FoodIngredientController {

    private final FoodIngredientService foodIngredientService;

    @ApiOperation(value = "식재료 등록", notes = "식재료 정보를 입력받아 DB에 저장한다.")
    @PostMapping()
    public ResponseEntity<BaseResponseDto> insertFoodIngredient(InsertFoodIngredientRequestDto insertFoodIngredientReqDto) {

        String res = foodIngredientService.saveFoodIngredient(insertFoodIngredientReqDto);

        if (res.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "식재료 등록 실패"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "식재료 등록 성공"));
    }

    @ApiOperation(value = "식재료 수정", notes = "식재료 정보를 입력받아 해당하는 식재료의 정보를 DB에 수정한다.")
    @PutMapping("/{foodIngredientId}")
    public ResponseEntity<BaseResponseDto> updateFoodIngredient(@PathVariable Long foodIngredientId,
                                                                UpdateFoodIngredientRequestDto updateFoodIngredientReqDto) {
        String res = foodIngredientService.updateFoodIngredient(foodIngredientId, updateFoodIngredientReqDto);

        if (res.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "식재료 수정 실패"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "식재료 수정 성공"));
    }

    @ApiOperation(value = "식재료 삭제", notes = "해당하는 식재료를 DB에서 삭제한다.")
    @DeleteMapping("/{foodIngredientId}")
    public ResponseEntity<BaseResponseDto> deleteFoodIngredient(@PathVariable Long foodIngredientId) {

        String res = foodIngredientService.deleteFoodIngredient(foodIngredientId);

        if (res.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "식재료 삭제 실패"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "식재료 삭제 성공"));
    }

    @ApiOperation(value = "식재료 목록", notes = "해당하는 냉장고의 식재료 List를 보여준다")
    @GetMapping("/{refrigeratorId}")
    public ResponseEntity<ListResponseDto<FoodIngredientResponseDto>> findFoodIngredientByRefrigeratorID(@ApiParam(value = "냉장고 아이디") @PathVariable Long refrigeratorId) {

        List<FoodIngredientResponseDto> response = foodIngredientService.findAllByRefrigeratorId(refrigeratorId);

        return ResponseEntity.ok(new ListResponseDto<FoodIngredientResponseDto>(200, "success", response));
    }
}
