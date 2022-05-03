package com.ssafy.andback.api.service;

import com.ssafy.andback.config.jwt.JwtAuthenticationProvider;
import com.ssafy.andback.core.domain.Recipe;
import com.ssafy.andback.core.domain.RecipeLike;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.repository.RecipeLikeRepository;
import com.ssafy.andback.core.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeLikeServiceImpl implements RecipeLikeService {

    private final RecipeLikeRepository recipeLikeRepository;
    private final RecipeRepository recipeRepository;

    public boolean addLike(User user, Long recipeId) {

        Recipe recipe = recipeRepository.findByRecipeId(recipeId).orElseThrow();

        // 중복 좋아요 방지
        if(isNotAlreadyLike(user, recipe)) {
            recipeLikeRepository.save(new RecipeLike(recipe, user));
            return true;
        }

        return false;
    }

    @Override
    public boolean removeLike(User user, Long recipeId) {

        Recipe recipe = recipeRepository.findByRecipeId(recipeId).orElseThrow();
        RecipeLike recipeLike;

        // 중복 좋아요 삭제 방지
        if(!isNotAlreadyLike(user, recipe)) {
            recipeLike = recipeLikeRepository.findByUserAndRecipe(user, recipe).get();
            recipeLikeRepository.delete(recipeLike);
            return true;
        }

        return false;
    }

    // 사용자가 이미 좋아요 한 레시피인지 체크
    private boolean isNotAlreadyLike(User user, Recipe recipe) {
        return recipeLikeRepository.findByUserAndRecipe(user, recipe).isEmpty();
    }
}
