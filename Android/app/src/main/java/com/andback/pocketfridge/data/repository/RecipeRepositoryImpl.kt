package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.mapper.CookingIngreMapper
import com.andback.pocketfridge.data.model.*
import com.andback.pocketfridge.data.repository.Recipe.RecipeRemoteDataSource
import com.andback.pocketfridge.domain.model.CookingIngre
import com.andback.pocketfridge.domain.repository.RecipeRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeRemoteDataSource: RecipeRemoteDataSource
) : RecipeRepository {

    override fun getRecipes(): Single<BaseResponse<List<RecipeEntity>>> {
        return recipeRemoteDataSource.getRecipes()
    }

    override fun getRecipeSteps(recipeId: Int): Single<BaseResponse<List<RecipeStepEntity>>> {
        return recipeRemoteDataSource.getRecipeSteps(recipeId)
    }

    override fun getCookingIngres(recipeId: Int): Single<BaseResponse<List<CookingIngre>>> {
        val single1 = recipeRemoteDataSource.getCookingIngres(recipeId)
        val single2 = recipeRemoteDataSource.getLackIngres(recipeId)

        var newResult = Single.zip(
            single1.subscribeOn(Schedulers.io()).map { it.data },
            single2.subscribeOn(Schedulers.io()).map { it.data },
            BiFunction<List<CookingIngreEntity>, List<LackIngreEntity>, BaseResponse<List<CookingIngre>>> { result1, result2 ->
                BaseResponse(data = CookingIngreMapper(result1, result2))
            })

        return newResult
    }
}