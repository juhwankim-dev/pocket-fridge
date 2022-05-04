package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.Recipe;
import com.ssafy.andback.core.domain.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    Optional<List<RecipeIngredient>> findAllByRecipe(Optional<Recipe> recipe);

}
