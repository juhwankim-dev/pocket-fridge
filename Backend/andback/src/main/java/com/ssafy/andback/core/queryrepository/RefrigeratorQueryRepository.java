package com.ssafy.andback.core.queryrepository;

import com.ssafy.andback.api.dto.response.RecipeIngredientRequireCountDto;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.RefrigeratorRepository;

import java.util.List;
import java.util.Map;

public interface RefrigeratorQueryRepository {

    List<Refrigerator> findAllRefrigeratorByUser(User user);

    Map<Long, Long> findAllRefrigeratorIngredientCount(User user);

}
