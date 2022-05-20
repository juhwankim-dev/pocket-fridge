package com.andback.pocketfridge.domain.usecase.ingredient

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.repository.IngreRepository
import io.reactivex.Single
import javax.inject.Inject

class GetIngreListUseCase @Inject constructor(
    private val ingreRepository: IngreRepository
) {
    operator fun invoke(fridgeId: Int): Single<BaseResponse<List<Ingredient>>>
        = ingreRepository.getIngreListByFridgeId(fridgeId)
}