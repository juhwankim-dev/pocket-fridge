package com.andback.pocketfridge.data.repository.Recipe

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.data.model.RecipeStepEntity
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import org.jetbrains.annotations.NotNull
import retrofit2.http.FieldMap
import retrofit2.http.Path

interface RecipeRemoteDataSource {
    fun getRecipes(): Single<BaseResponse<List<RecipeEntity>>>
    fun getRecipeSteps(recipeId: Int): Single<BaseResponse<List<RecipeStepEntity>>>
}