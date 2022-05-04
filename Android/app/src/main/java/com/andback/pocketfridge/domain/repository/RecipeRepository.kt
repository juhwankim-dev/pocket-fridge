package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.CookingIngreEntity
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.data.model.RecipeStepEntity
import io.reactivex.Single

interface RecipeRepository {
    fun getRecipes(): Single<BaseResponse<List<RecipeEntity>>>
    fun getRecipeSteps(recipeId: Int): Single<BaseResponse<List<RecipeStepEntity>>>
    fun getCookingIngres(recipeId: Int): Single<BaseResponse<List<CookingIngreEntity>>>
}