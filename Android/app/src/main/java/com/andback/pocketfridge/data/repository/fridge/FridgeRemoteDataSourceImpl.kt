package com.andback.pocketfridge.data.repository.fridge

import com.andback.pocketfridge.data.api.FridgeApi
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class FridgeRemoteDataSourceImpl @Inject constructor(
    private val fridgeApi: FridgeApi
): FridgeRemoteDataSource {
    override fun getFridges(): Single<BaseResponse<List<FridgeEntity>>> {
        return fridgeApi.getFridges()
    }

    override fun createFridge(name: String): Single<BaseResponse<Unit>> {
        return fridgeApi.createFridge(name)
    }

    override fun deleteFridge(id: Int): Single<BaseResponse<Unit>> {
        return fridgeApi.deleteFridge(id)
    }
}