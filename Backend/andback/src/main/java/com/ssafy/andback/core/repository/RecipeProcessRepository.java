package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.Recipe;
import com.ssafy.andback.core.domain.RecipeProcess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * RecipeProcessRepository
 * 레시피 과정 정보 JPA 레포지토리
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-05-02
 * 마지막 수정일 2022-05-02
 **/

public interface RecipeProcessRepository extends JpaRepository<RecipeProcess, Long> {

    Optional<List<RecipeProcess>> findAllByRecipe(Recipe recipe);

}
