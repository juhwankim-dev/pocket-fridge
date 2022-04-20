/**
* FoodIngredientRepository
* DB 에서 식재료 정보에 접근하기 위한 Repository
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/04/19
* 마지막 수정일 2022/04/19
**/
package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.FoodIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodIngredientRepository extends JpaRepository<FoodIngredient, Long> {

    FoodIngredient findByFoodIngredientId(Long foodIngredientId);
}
