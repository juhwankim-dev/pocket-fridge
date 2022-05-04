package com.andback.pocketfridge.domain.usecase.ingredient

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.repository.IngreRepository
import com.andback.pocketfridge.present.utils.IngreChecker
import io.reactivex.Single
import javax.inject.Inject

class UpdateIngreUseCase @Inject constructor(
    private val ingreRepository: IngreRepository
) {
    operator fun invoke(ingredient: Ingredient): Single<BaseResponse<Any>> {
        return checkValidationThenUpload(ingredient)
    }

    private fun checkValidationThenUpload(ingredient: Ingredient): Single<BaseResponse<Any>> {
        return try {
            IngreChecker.check(ingredient)
            ingreRepository.updateIngre(ingredient)
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