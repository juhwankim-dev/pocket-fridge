package com.andback.pocketfridge.data.repository.fridge

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Observable

interface FridgeRemoteDataSource {
    fun getFridges(email: String): Observable<BaseResponse<List<FridgeEntity>>>
}