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

import com.ssafy.andback.api.dto.FoodIngredientDto;
import com.ssafy.andback.core.domain.foodingredient.FoodIngredient;
import com.ssafy.andback.core.repository.FoodIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodIngredientServiceImpl implements FoodIngredientService{

    private final FoodIngredientRepository foodIngredientRepository;

    public String saveFoodIngredient(FoodIngredientDto foodIngredientDto) {

        // 식재료 등록
        FoodIngredient foodIngredient = new FoodIngredient();
        foodIngredient.setFoodIngredientName(foodIngredientDto.getFoodIngredientName());
        foodIngredient.setFoodIngredientExp(foodIngredientDto.getFoodIngredientExp());
        foodIngredient.setFoodIngredientCategory(foodIngredientDto.getFoodIngredientCategory());
        foodIngredient.setFoodIngredientCount(foodIngredientDto.getFoodIngredientCount());
        foodIngredient.setFoodIngredientDate(foodIngredientDto.getFoodIngredientDate());
        foodIngredient.setFoodIngredientWay(foodIngredientDto.getFoodIngredientWay());

        if (foodIngredient.getFoodIngredientName().equals("") || foodIngredient.getFoodIngredientExp().equals("")
                || foodIngredient.getFoodIngredientCategory().equals("") || foodIngredient.getFoodIngredientCount() <= 0 ||
                foodIngredient.getFoodIngredientDate().equals("") || foodIngredient.getFoodIngredientWay().equals("")) {
            return "fail";
        }

        foodIngredientRepository.save(foodIngredient);
        return "success";
    }
}
