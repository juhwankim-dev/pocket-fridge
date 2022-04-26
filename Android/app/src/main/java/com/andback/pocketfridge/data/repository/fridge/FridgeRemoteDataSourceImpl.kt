package com.andback.pocketfridge.data.repository.fridge

import com.andback.pocketfridge.data.api.FridgeApi
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Observable
import javax.inject.Inject

class FridgeRemoteDataSourceImpl @Inject constructor(
    private val fridgeApi: FridgeApi
): FridgeRemoteDataSource {
    override fun getFridges(email: String): Observable<BaseResponse<List<FridgeEntity>>> {
        return fridgeApi.getFridges(email)
    }
}