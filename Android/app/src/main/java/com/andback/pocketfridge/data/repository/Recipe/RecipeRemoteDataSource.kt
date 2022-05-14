package com.andback.pocketfridge.data.repository.Recipe

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.CookingIngreEntity
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.data.model.RecipeStepEntity
import com.andback.pocketfridge.data.model.LackIngreEntity
import io.reactivex.Single

interface RecipeRemoteDataSource {
    fun getRecipes(): Single<BaseResponse<List<RecipeEntity>>>
    fun getRecipeSteps(recipeId: Int): Single<BaseResponse<List<RecipeStepEntity>>>
    fun getCookingIngres(recipeId: Int): Single<BaseResponse<List<CookingIngreEntity>>>
    fun getLackIngres(recipeId: Int): Single<BaseResponse<List<LackIngreEntity>>>
}