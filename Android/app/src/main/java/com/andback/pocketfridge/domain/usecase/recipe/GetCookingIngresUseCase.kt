package com.andback.pocketfridge.domain.usecase.recipe

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.model.CookingIngre
import com.andback.pocketfridge.domain.repository.RecipeRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCookingIngresUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(recipeId: Int): Single<BaseResponse<List<CookingIngre>>> =
        recipeRepository.getCookingIngres(recipeId)
}