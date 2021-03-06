/**
 * FoodIngredientService
 * 식재료 관련 비즈니스 로직을 위한 Service
 *
 * @author 문관필
 * @version 1.0.0
 * 생성일 2022/04/19
 * 마지막 수정일 2022/04/19
 **/
package com.ssafy.andback.api.service;

import com.ssafy.andback.api.constant.ErrorCode;
import com.ssafy.andback.api.dto.request.InsertFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.request.UpdateFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.response.FoodIngredientResponseDto;
import com.ssafy.andback.api.dto.response.MainCategoryResponseDto;
import com.ssafy.andback.api.dto.response.SubCategoryResponseDto;
import com.ssafy.andback.api.exception.CustomException;
import com.ssafy.andback.core.domain.*;
import com.ssafy.andback.core.repository.FoodIngredientRepository;
import com.ssafy.andback.core.repository.MainCategoryRepository;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import com.ssafy.andback.core.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodIngredientServiceImpl implements FoodIngredientService {

    private final FoodIngredientRepository foodIngredientRepository;
    private final RefrigeratorRepository refrigeratorRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final MainCategoryRepository mainCategoryRepository;

    public String saveFoodIngredient(InsertFoodIngredientRequestDto insertFoodIngredientReqDto) {

        // 식재료 등록
        FoodIngredient foodIngredient = new FoodIngredient();

        foodIngredient.setFoodIngredientName(insertFoodIngredientReqDto.getFoodIngredientName());
        foodIngredient.setFoodIngredientExp(insertFoodIngredientReqDto.getFoodIngredientExp());
        foodIngredient.setFoodIngredientCount(1.0f);
        foodIngredient.setFoodIngredientDate(insertFoodIngredientReqDto.getFoodIngredientDate());
        foodIngredient.setFoodIngredientWay(insertFoodIngredientReqDto.getFoodIngredientWay());

        if (foodIngredient.getFoodIngredientName().equals("") || foodIngredient.getFoodIngredientExp().equals("")
                || foodIngredient.getFoodIngredientDate().equals("") || foodIngredient.getFoodIngredientDate().isAfter(foodIngredient.getFoodIngredientExp())
                || foodIngredient.getFoodIngredientWay().equals("") || refrigeratorRepository.findByRefrigeratorId(insertFoodIngredientReqDto.getRefrigeratorId()) == null
        ) {
            return "fail";
        }

        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(insertFoodIngredientReqDto.getRefrigeratorId());

        refrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND)
        );

        Optional<SubCategory> subCategory = subCategoryRepository.findById(insertFoodIngredientReqDto.getSubCategoryId());
        foodIngredient.setRefrigerator(refrigerator.get());
        foodIngredient.setSubCategory(subCategory.get());
        foodIngredientRepository.save(foodIngredient);
        return "success";
    }

    @Override
    public String updateFoodIngredient(UpdateFoodIngredientRequestDto updateFoodIngredientReqDto) {

        FoodIngredient foodIngredient = foodIngredientRepository.findByFoodIngredientId(updateFoodIngredientReqDto.getFoodIngredientId());

        // 식재료 수정
        foodIngredient.setFoodIngredientName(updateFoodIngredientReqDto.getFoodIngredientName());
        foodIngredient.setFoodIngredientExp(updateFoodIngredientReqDto.getFoodIngredientExp());
        foodIngredient.setFoodIngredientCount(1.0f);
        foodIngredient.setFoodIngredientDate(updateFoodIngredientReqDto.getFoodIngredientDate());
        foodIngredient.setFoodIngredientWay(updateFoodIngredientReqDto.getFoodIngredientWay());


        if (foodIngredient.getFoodIngredientName().equals("") || foodIngredient.getFoodIngredientExp().equals("")
                || foodIngredient.getFoodIngredientDate().equals("") || foodIngredient.getFoodIngredientWay().equals("")
                || refrigeratorRepository.findByRefrigeratorId(updateFoodIngredientReqDto.getRefrigeratorId()) == null) {
            throw new CustomException(ErrorCode.FAIL_CHANGE_INGREDIENT);
        }

        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(updateFoodIngredientReqDto.getRefrigeratorId());


        foodIngredient.setRefrigerator(refrigerator.get());
        foodIngredientRepository.save(foodIngredient);
        return "success";
    }

    @Override
    public String deleteFoodIngredient(Long foodIngredientId) {
        FoodIngredient foodIngredient = foodIngredientRepository.findByFoodIngredientId(foodIngredientId);

        if (foodIngredient == null) {
            return "fail";
        }

        foodIngredientRepository.delete(foodIngredient);
        return "success";
    }

    @Override
    public List<FoodIngredientResponseDto> findAllByRefrigeratorId(Long refrigeratorId) {

        Optional<Refrigerator> refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);

        refrigerator.orElseThrow(
                () -> new CustomException(ErrorCode.REFRIGERATOR_NOT_FOUND)
        );

        List<FoodIngredient> allByRefrigerator = foodIngredientRepository.findAllByRefrigerator(refrigerator.get());

        List<FoodIngredientResponseDto> res = new ArrayList<>();

        for (FoodIngredient temp : allByRefrigerator) {
            res.add(FoodIngredientResponseDto.builder()
                    .foodIngredientId(temp.getFoodIngredientId())
                    .foodIngredientDate(temp.getFoodIngredientDate())
                    .foodIngredientExp(temp.getFoodIngredientExp())
                    .foodIngredientName(temp.getFoodIngredientName())
                    .foodIngredientWay(temp.getFoodIngredientWay())
                    .refrigeratorId(temp.getRefrigerator().getRefrigeratorId())
                    .subCategoryId(temp.getSubCategory().getSubCategory())
                    .mainCategoryId(temp.getSubCategory().getMainCategory().getMainCategory())
                    .build());
        }

        return res;
    }

    @Override
    public List<MainCategoryResponseDto> findAllMainCategory() {

        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        List<MainCategoryResponseDto> res = new ArrayList<>();

        for (MainCategory temp : mainCategoryList) {
            res.add(MainCategoryResponseDto.builder()
                    .mainCategoryId(temp.getMainCategory())
                    .mainCategoryName(temp.getMainCategoryName())
                    .build());
        }

        return res;
    }

    @Override
    public List<SubCategoryResponseDto> findAllSubCategory() {

        List<SubCategory> subCategoryList = subCategoryRepository.findAll();

        List<SubCategoryResponseDto> res = new ArrayList<>();

        for (SubCategory temp : subCategoryList) {
            res.add(SubCategoryResponseDto.builder()
                    .mainCategoryId(temp.getMainCategory().getMainCategory())
                    .subCategoryId(temp.getSubCategory())
                    .subCategoryName(temp.getSubCategoryName())
                    .subCategoryUrl(temp.getSubCategoryUrl())
                    .build());
        }

        return res;
    }

    @Override
    public List<FoodIngredientResponseDto> findAllIngredientList(User user) {

        List<FoodIngredient> foodIngredientList = foodIngredientRepository.findAllIngredientList(user);

        List<FoodIngredientResponseDto> res = new ArrayList<>();

        for (FoodIngredient temp : foodIngredientList) {
            res.add(FoodIngredientResponseDto.builder()
                    .foodIngredientId(temp.getFoodIngredientId())
                    .foodIngredientDate(temp.getFoodIngredientDate())
                    .foodIngredientExp(temp.getFoodIngredientExp())
                    .foodIngredientName(temp.getFoodIngredientName())
                    .foodIngredientWay(temp.getFoodIngredientWay())
                    .refrigeratorId(temp.getRefrigerator().getRefrigeratorId())
                    .subCategoryId(temp.getSubCategory().getSubCategory())
                    .mainCategoryId(temp.getSubCategory().getMainCategory().getMainCategory())
                    .build());
        }

        return res;
    }


}
