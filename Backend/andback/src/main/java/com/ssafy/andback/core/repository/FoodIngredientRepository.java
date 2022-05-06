/**
* FoodIngredientRepository
* DB 에서 식재료 정보에 접근하기 위한 Repository
*
* @author 문관필, 김다은
* @version 1.0.0
* 생성일 2022/04/19
* 마지막 수정일 2022/05/03
**/
package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.FoodIngredient;
import com.ssafy.andback.core.domain.Refrigerator;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodIngredientRepository extends JpaRepository<FoodIngredient, Long> {

    FoodIngredient findByFoodIngredientId(Long foodIngredientId);

    List<FoodIngredient> findAllByRefrigerator(Refrigerator refrigeratorId);

    void deleteFoodIngredientsByRefrigerator(Refrigerator refrigeratorId);

    List<FoodIngredient> findByRefrigerator(Refrigerator refrigerator);
}
