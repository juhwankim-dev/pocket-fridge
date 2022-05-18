package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.RecipeStepEntity
import com.andback.pocketfridge.domain.model.CookingIngre
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.domain.model.RecipeStep
import io.reactivex.Single

interface RecipeRepository {
    fun getRecipes(): Single<BaseResponse<List<Recipe>>>
    fun getRecipeSteps(recipeId: Int): Single<BaseResponse<List<RecipeStep>>>
    fun getCookingIngres(recipeId: Int): Single<BaseResponse<List<CookingIngre>>>
}