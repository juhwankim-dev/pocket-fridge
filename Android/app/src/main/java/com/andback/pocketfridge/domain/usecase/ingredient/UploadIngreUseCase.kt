package com.andback.pocketfridge.domain.usecase.ingredient

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.repository.IngreRepository
import com.andback.pocketfridge.present.utils.IngreChecker
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception
import javax.inject.Inject

class UploadIngreUseCase @Inject constructor(
    private val ingreRepository: IngreRepository
) {
    fun uploadIngre(ingredient: Ingredient): Single<BaseResponse<Any>> {
        return checkValidationThenUpload(ingredient)
    }

    private fun checkValidationThenUpload(ingredient: Ingredient): Single<BaseResponse<Any>> {
        return try {
            IngreChecker.check(ingredient)
            val ingredientEntity = IngreEntityForUpload(foodIngredientName = ingredient.name, foodIngredientExp = ingredient.expiryDate, foodIngredientDate = ingredient.purchasedDate, refrigeratorId = ingredient.fridgeId, subCategoryId = ingredient.category, foodIngredientWay = ingredient.storage.value)
            ingreRepository.uploadIngre(ingreEntityForUpload = ingredientEntity)
        } catch (e: Throwable) {
            makeValidationErrorObservable(e)
        }
    }

    private fun makeValidationErrorObservable(e: Throwable): Single<BaseResponse<Any>> {
        return Single.create { emitter ->
            emitter.onError(e)
        }
    }
}