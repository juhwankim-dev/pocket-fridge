package com.andback.pocketfridge.domain.usecase.recipe

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.data.model.RecipeStepEntity
import com.andback.pocketfridge.domain.repository.RecipeRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    fun getRecipes(): Single<BaseResponse<List<RecipeEntity>>> = recipeRepository.getRecipes()
}