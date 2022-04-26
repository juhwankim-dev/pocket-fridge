package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Observable

interface FridgeRepository {
    fun getFridges(email: String): Observable<BaseResponse<List<FridgeEntity>>>
}