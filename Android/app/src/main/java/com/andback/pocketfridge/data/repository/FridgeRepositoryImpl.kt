package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.ShareUserEntity
import com.andback.pocketfridge.data.repository.fridge.FridgeRemoteDataSource
import com.andback.pocketfridge.domain.repository.FridgeRepository
import io.reactivex.Single
import javax.inject.Inject

class FridgeRepositoryImpl @Inject constructor(
    private val dataSource: FridgeRemoteDataSource
): FridgeRepository {
    override fun getFridges(): Single<BaseResponse<List<FridgeEntity>>> {
        return dataSource.getFridges()
    }

    override fun createFridge(name: String): Single<BaseResponse<Unit>> {
        return dataSource.createFridge(name)
    }

    override fun updateFridgeName(id: Int, name: String): Single<BaseResponse<Unit>> {
        return dataSource.updateFridgeName(id, name)
    }

    override fun deleteFridge(id: Int): Single<BaseResponse<Unit>> {
        return dataSource.deleteFridge(id)
    }

    override fun getFridgeMembers(id: Int): Single<BaseResponse<List<ShareUserEntity>>> {
        return dataSource.getFridgeMembers(id)
    }
}