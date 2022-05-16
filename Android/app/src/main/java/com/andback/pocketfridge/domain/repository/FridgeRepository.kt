package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.ShareUserEntity
import io.reactivex.Single

interface FridgeRepository {
    fun getFridges(): Single<BaseResponse<List<FridgeEntity>>>

    fun createFridge(name: String): Single<BaseResponse<Unit>>

    fun updateFridgeName(id: Int, name: String): Single<BaseResponse<Unit>>

    fun deleteFridge(id: Int): Single<BaseResponse<Unit>>

<<<<<<< HEAD
    fun shareFridge(email: String, fridgeId: Int): Single<BaseResponse<String>>
=======
    fun getFridgeMembers(id: Int): Single<BaseResponse<List<ShareUserEntity>>>
>>>>>>> develop
}