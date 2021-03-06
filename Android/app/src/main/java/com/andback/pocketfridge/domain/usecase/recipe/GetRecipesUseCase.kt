package com.andback.pocketfridge.domain.usecase.recipe

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.domain.repository.RecipeRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(): Single<BaseResponse<List<Recipe>>> = recipeRepository.getRecipes()
}