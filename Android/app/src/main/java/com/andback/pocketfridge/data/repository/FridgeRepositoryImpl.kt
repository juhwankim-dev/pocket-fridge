package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
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
}