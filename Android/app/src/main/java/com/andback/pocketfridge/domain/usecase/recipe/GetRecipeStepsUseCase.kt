package com.andback.pocketfridge.domain.usecase.recipe

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.data.model.RecipeStepEntity
import com.andback.pocketfridge.domain.repository.RecipeRepository
import io.reactivex.Single
import javax.inject.Inject

class GetRecipesStepsUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    fun getRecipeSteps(recipeId: Int): Single<BaseResponse<List<RecipeStepEntity>>> = recipeRepository.getRecipeSteps(recipeId)
}