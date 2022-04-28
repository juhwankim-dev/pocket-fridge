package com.andback.pocketfridge.domain.usecase.ingredient

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.repository.IngreRepository
import com.andback.pocketfridge.present.utils.IngreChecker
import io.reactivex.Observable
import java.lang.Exception
import javax.inject.Inject

class UploadIngreUseCase @Inject constructor(
    val ingreRepository: IngreRepository
) {
    fun uploadIngre(ingredient: Ingredient): Observable<BaseResponse<Any>> {
        return checkValidationThenUpload(ingredient)
    }

    private fun checkValidationThenUpload(ingredient: Ingredient): Observable<BaseResponse<Any>> {
        return try {
            IngreChecker.check(ingredient)
            val ingredientEntity = IngreEntityForUpload(foodIngredientName = ingredient.name, foodIngredientExp = ingredient.expiryDate, foodIngredientDate = ingredient.purchasedDate, refrigeratorId = ingredient.fridgeId, category = ingredient.category, foodIngredientWay = ingredient.storage.value)
            ingreRepository.uploadIngre(ingreEntityForUpload = ingredientEntity)
        } catch (e: Throwable) {
            makeValidationErrorObservable(e)
        }
    }

    private fun makeValidationErrorObservable(e: Throwable): Observable<BaseResponse<Any>> {
        return Observable.create { emitter ->
            emitter.onError(e)
        }
    }
}