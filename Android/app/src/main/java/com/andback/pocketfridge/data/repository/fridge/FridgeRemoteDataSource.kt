package com.andback.pocketfridge.data.repository.fridge

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import io.reactivex.Single
import org.jetbrains.annotations.NotNull

interface FridgeRemoteDataSource {
    fun getFridges(): Single<BaseResponse<List<FridgeEntity>>>

    fun createFridge(@NotNull name: String): Single<BaseResponse<Unit>>

    fun updateFridgeName(@NotNull id: Int, @NotNull name: String): Single<BaseResponse<Unit>>

    fun deleteFridge(@NotNull id: Int): Single<BaseResponse<Unit>>

    fun shareFridge(email: String, fridgeId: Int): Single<BaseResponse<String>>
}