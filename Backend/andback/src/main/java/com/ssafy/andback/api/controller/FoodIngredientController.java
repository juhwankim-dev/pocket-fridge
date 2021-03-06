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

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.request.UpdateFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.response.*;
import com.ssafy.andback.api.dto.request.InsertFoodIngredientRequestDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.api.service.FoodIngredientService;
import com.ssafy.andback.core.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = {"02. 식재료 API"})
@RestController
@RequestMapping("/foodingredient")
@RequiredArgsConstructor
public class FoodIngredientController {

    private final FoodIngredientService foodIngredientService;

    @ApiOperation(value = "식재료 등록", notes = "식재료 정보를 입력받아 DB에 저장한다.")
    @PostMapping()
    public ResponseEntity<BaseResponseDto> insertFoodIngredient(@RequestBody InsertFoodIngredientRequestDto insertFoodIngredientReqDto) {

        String res = foodIngredientService.saveFoodIngredient(insertFoodIngredientReqDto);

        if (res.equals("fail")) {
            return ResponseEntity.status(401).body(BaseResponseDto.of(401, "식재료 등록 실패"));
        }
        return ResponseEntity.ok(BaseResponseDto.of(200, "식재료 등록 성공"));
    }

    @ApiOperation(value = "식재료 수정", notes = "식재료 정보를 입력받아 해당하는 식재료의 정보를 DB에 수정한다.")
    @PutMapping
    public ResponseEntity<BaseResponseDto> updateFoodIngredient(@RequestBody UpdateFoodIngredientRequestDto updateFoodIngredientReqDto) {
        String res = foodIngredientService.updateFoodIngredient(updateFoodIngredientReqDto);

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

    @ApiOperation(value = "메인 카테고리 목록", notes = "메인 카테고리 목록을 보여준다")
    @GetMapping("/maincategory")
    public ResponseEntity<ListResponseDto<MainCategoryResponseDto>> findAllMainCategory() {

        List<MainCategoryResponseDto> response = foodIngredientService.findAllMainCategory();

        return ResponseEntity.ok(new ListResponseDto<MainCategoryResponseDto>(200, "success", response));
    }

    @ApiOperation(value = "서브 카테고리 목록", notes = "서브 카테고리 목록을 보여준다")
    @GetMapping("/subcategory")
    public ResponseEntity<ListResponseDto<SubCategoryResponseDto>> findAllSubCategory() {

        List<SubCategoryResponseDto> response = foodIngredientService.findAllSubCategory();

        return ResponseEntity.ok(new ListResponseDto<SubCategoryResponseDto>(200, "success", response));
    }

    @ApiOperation(value = "모든 식재료 목록", notes = "가지고 있는 모든 식재료를 반환한다")
    @GetMapping
    public ResponseEntity<ListResponseDto<FoodIngredientResponseDto>> findAllIngredientList(@ApiIgnore Authentication authentication) {
        if (authentication == null) {
            throw new CustomException(ErrorCode.NOT_AUTH_TOKEN);
        }

        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        List<FoodIngredientResponseDto> response = foodIngredientService.findAllIngredientList(user);

        return ResponseEntity.ok(new ListResponseDto<FoodIngredientResponseDto>(200, "success", response));
    }
}
