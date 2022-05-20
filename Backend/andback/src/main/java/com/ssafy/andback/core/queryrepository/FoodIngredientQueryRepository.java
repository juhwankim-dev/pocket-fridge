package com.ssafy.andback.core.queryrepository;


import com.ssafy.andback.core.domain.FoodIngredient;
import com.ssafy.andback.core.domain.User;

import java.util.List;

public interface FoodIngredientQueryRepository {

    List<FoodIngredient> findAllIngredientList(User user);

}
