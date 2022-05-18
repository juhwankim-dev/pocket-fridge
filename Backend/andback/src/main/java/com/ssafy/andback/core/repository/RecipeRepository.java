/**
 * RecipeRepository
 * 레시피 JPA Repository
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-02
 * 마지막 수정일 2022-05-02
 **/
package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.Recipe;
import com.ssafy.andback.core.queryrepository.RefrigeratorQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RefrigeratorQueryRepository {

    Optional<Recipe> findByRecipeId(Long recipeId);
}
