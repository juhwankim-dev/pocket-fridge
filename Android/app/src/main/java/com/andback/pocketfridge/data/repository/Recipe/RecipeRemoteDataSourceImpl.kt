package com.andback.pocketfridge.data.repository.Recipe

import com.andback.pocketfridge.data.api.RecipeApi
import com.andback.pocketfridge.data.api.UserApi
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.data.model.RecipeStepEntity
import io.reactivex.Single
import javax.inject.Inject

class RecipeRemoteDataSourceImpl @Inject constructor(private val recipeApi: RecipeApi) : RecipeRemoteDataSource {
    override fun getRecipes(): Single<BaseResponse<List<RecipeEntity>>> {
        return recipeApi.getRecipes()
    }

    override fun getRecipeSteps(recipeId: Int): Single<BaseResponse<List<RecipeStepEntity>>> {
        return recipeApi.getRecipeSteps(recipeId)
    }
}