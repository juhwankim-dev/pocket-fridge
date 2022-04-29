package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSource
import com.andback.pocketfridge.domain.repository.IngreRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class IngreRepositoryImpl @Inject constructor(private val ingreRemoteDataSource: IngreRemoteDataSource): IngreRepository {
    override fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Observable<BaseResponse<Any>> {
        return ingreRemoteDataSource.uploadIngre(ingreEntityForUpload)
    }

    override fun getIngreListByFridgeId(fridgeId: Int): Single<BaseResponse<List<IngreEntity>>> {
        return ingreRemoteDataSource.getIngreListByFridgeId(fridgeId)
    }
}