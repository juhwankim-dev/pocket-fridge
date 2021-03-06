package com.andback.pocketfridge.data.repository.Recipe

import com.andback.pocketfridge.data.api.RecipeApi
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.CookingIngreEntity
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.data.model.RecipeStepEntity
import com.andback.pocketfridge.data.model.LackIngreEntity
import io.reactivex.Single
import javax.inject.Inject

class RecipeRemoteDataSourceImpl @Inject constructor(private val recipeApi: RecipeApi) : RecipeRemoteDataSource {
    override fun getRecipes(): Single<BaseResponse<List<RecipeEntity>>> {
        return recipeApi.getRecipes()
    }

    override fun getRecipeSteps(recipeId: Int): Single<BaseResponse<List<RecipeStepEntity>>> {
        return recipeApi.getRecipeSteps(recipeId)
    }

    override fun getCookingIngres(recipeId: Int): Single<BaseResponse<List<CookingIngreEntity>>> {
        return recipeApi.getCookingIngres(recipeId)
    }

    override fun getLackIngres(recipeId: Int): Single<BaseResponse<List<LackIngreEntity>>> {
        return recipeApi.getLackIngres(recipeId)
    }

    override fun getRecommendList(): Single<BaseResponse<List<Int>>> {
        return recipeApi.getRecommendList()
    }
}