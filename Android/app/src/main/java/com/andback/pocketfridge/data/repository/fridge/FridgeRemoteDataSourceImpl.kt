package com.andback.pocketfridge.data.repository.fridge

import com.andback.pocketfridge.data.api.FridgeApi
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.ShareUserEntity
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

    override fun updateFridgeName(id: Int, name: String): Single<BaseResponse<Unit>> {
        return fridgeApi.updateFridgeName(id, name)
    }

    override fun deleteFridge(id: Int): Single<BaseResponse<Unit>> {
        return fridgeApi.deleteFridge(id)
    }

    override fun shareFridge(email: String, fridgeId: Int): Single<BaseResponse<String>> {
        return fridgeApi.shareFridge(email, fridgeId)
    }

    override fun getFridgeMembers(id: Int): Single<BaseResponse<List<ShareUserEntity>>> {
        return fridgeApi.getFridgeMembers(id)
    }

    override fun deleteFridgeMember(id: Int, email: String): Single<BaseResponse<String>> {
        return fridgeApi.deleteFridgeMember(id, email)
    }
}