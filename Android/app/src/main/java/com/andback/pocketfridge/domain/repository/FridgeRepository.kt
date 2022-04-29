package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Single

interface FridgeRepository {
    fun getFridges(email: String): Single<BaseResponse<List<FridgeEntity>>>
}