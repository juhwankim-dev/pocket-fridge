package com.andback.pocketfridge.domain.usecase.ingredient

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.IngreRepository
import io.reactivex.Single
import javax.inject.Inject

class DeleteIngreUseCase @Inject constructor(
    private val ingreRepository: IngreRepository
) {
    operator fun invoke(ingreId: Int): Single<BaseResponse<Any>> {
        return ingreRepository.deleteIngreById(ingreId)
    }
}