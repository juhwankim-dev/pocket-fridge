package com.andback.pocketfridge.data.repository.fridge

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Single

interface FridgeRemoteDataSource {
    fun getFridges(email: String): Single<BaseResponse<List<FridgeEntity>>>
}