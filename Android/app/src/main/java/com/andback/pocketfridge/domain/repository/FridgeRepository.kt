package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Single

interface FridgeRepository {
    fun getFridges(): Single<BaseResponse<List<FridgeEntity>>>

    fun createFridge(name: String): Single<BaseResponse<Unit>>

    fun updateFridgeName(id: Int, name: String): Single<BaseResponse<Unit>>

    fun deleteFridge(id: Int): Single<BaseResponse<Unit>>
}