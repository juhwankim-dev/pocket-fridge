package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSourceImpl
import com.andback.pocketfridge.domain.repository.IngreRepository
import io.reactivex.Single
import javax.inject.Inject

class IngreRepositoryImpl @Inject constructor(val ingreRemoteDataSource: IngreRemoteDataSourceImpl): IngreRepository {
    override fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Single<BaseResponse<Any>> {
        return ingreRemoteDataSource.uploadIngre(ingreEntityForUpload)
    }
}