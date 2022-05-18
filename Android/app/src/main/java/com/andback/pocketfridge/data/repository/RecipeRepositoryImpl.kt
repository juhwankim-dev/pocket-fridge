package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.mapper.CookingIngreMapper
import com.andback.pocketfridge.data.mapper.RecipeMapper
import com.andback.pocketfridge.data.mapper.RecipeStepMapper
import com.andback.pocketfridge.data.model.*
import com.andback.pocketfridge.data.repository.Recipe.RecipeRemoteDataSource
import com.andback.pocketfridge.domain.model.CookingIngre
import com.andback.pocketfridge.domain.model.Recipe
import com.andback.pocketfridge.domain.model.RecipeStep
import com.andback.pocketfridge.domain.repository.RecipeRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeRemoteDataSource: RecipeRemoteDataSource
) : RecipeRepository {

    override fun getRecipes(): Single<BaseResponse<List<Recipe>>> {
        val single1 = recipeRemoteDataSource.getRecipes()
        val single2 = recipeRemoteDataSource.getRecommendList()

        var newResult = Single.zip(
            single1.subscribeOn(Schedulers.io()).map { it.data },
            single2.subscribeOn(Schedulers.io()).map { it.data },
            BiFunction<List<RecipeEntity>, List<Int>, BaseResponse<List<Recipe>>> { result1, result2 ->
                BaseResponse(data = RecipeMapper(result1, result2))
            })

        return newResult
    }

    override fun getRecipeSteps(recipeId: Int): Single<BaseResponse<List<RecipeStep>>> {
        return recipeRemoteDataSource.getRecipeSteps(recipeId).map { it
            val newList = RecipeStepMapper(it.data)

            BaseResponse(it.message, it.status, newList)
        }
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