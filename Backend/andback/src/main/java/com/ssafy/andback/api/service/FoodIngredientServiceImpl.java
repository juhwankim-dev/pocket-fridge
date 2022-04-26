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

import com.ssafy.andback.api.dto.request.InsertFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.request.UpdateFoodIngredientRequestDto;
import com.ssafy.andback.api.dto.response.FoodIngredientResponseDto;
import com.ssafy.andback.core.domain.FoodIngredient;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.repository.FoodIngredientRepository;
import com.ssafy.andback.core.repository.RefrigeratorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodIngredientServiceImpl implements FoodIngredientService {

    private final FoodIngredientRepository foodIngredientRepository;
    private final RefrigeratorRepository refrigeratorRepository;

    public String saveFoodIngredient(InsertFoodIngredientRequestDto insertFoodIngredientReqDto) {

        // 식재료 등록
        FoodIngredient foodIngredient = new FoodIngredient();
        Refrigerator refrigerator;

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

        refrigerator = refrigeratorRepository.findByRefrigeratorId(insertFoodIngredientReqDto.getRefrigeratorId());
        foodIngredient.setRefrigerator(refrigerator);
        foodIngredientRepository.save(foodIngredient);
        return "success";
    }

    @Override
    public String updateFoodIngredient(Long foodIngredientId, UpdateFoodIngredientRequestDto updateFoodIngredientReqDto) {

        FoodIngredient foodIngredient = foodIngredientRepository.findByFoodIngredientId(foodIngredientId);
        Refrigerator refrigerator;

        // 식재료 수정
        foodIngredient.setFoodIngredientName(updateFoodIngredientReqDto.getFoodIngredientName());
        foodIngredient.setFoodIngredientExp(updateFoodIngredientReqDto.getFoodIngredientExp());
        foodIngredient.setFoodIngredientCount(1.0f);
        foodIngredient.setFoodIngredientDate(updateFoodIngredientReqDto.getFoodIngredientDate());
        foodIngredient.setFoodIngredientWay(updateFoodIngredientReqDto.getFoodIngredientWay());


        if (foodIngredient.getFoodIngredientName().equals("") || foodIngredient.getFoodIngredientExp().equals("")
                || foodIngredient.getFoodIngredientDate().equals("") || foodIngredient.getFoodIngredientWay().equals("")
                || refrigeratorRepository.findByRefrigeratorId(updateFoodIngredientReqDto.getRefrigeratorId()) == null) {
            return "fail";
        }

        refrigerator = refrigeratorRepository.findByRefrigeratorId(updateFoodIngredientReqDto.getRefrigeratorId());
        foodIngredient.setRefrigerator(refrigerator);
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

        Refrigerator refrigerator = refrigeratorRepository.findByRefrigeratorId(refrigeratorId);

        List<FoodIngredient> allByRefrigerator = foodIngredientRepository.findAllByRefrigerator(refrigerator);

        List<FoodIngredientResponseDto> res = new ArrayList<>();

        for (FoodIngredient temp : allByRefrigerator) {
            res.add(FoodIngredientResponseDto.builder()
                    .foodIngredientDate(temp.getFoodIngredientDate())
                    .foodIngredientExp(temp.getFoodIngredientExp())
                    .foodIngredientName(temp.getFoodIngredientName())
                    .build());
        }

        return res;
    }
}
