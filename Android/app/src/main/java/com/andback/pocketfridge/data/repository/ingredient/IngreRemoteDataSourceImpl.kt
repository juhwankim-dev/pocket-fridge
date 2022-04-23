package com.andback.pocketfridge.data.repository.ingredient

import com.andback.pocketfridge.data.api.IngreApi
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import io.reactivex.Single
import javax.inject.Inject

class IngreRemoteDataSourceImpl @Inject constructor(val ingreApi: IngreApi): IngreRemoteDataSource {
    override fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Single<BaseResponse<Any>> {
        return ingreApi.uploadIngre(ingreEntityForUpload)
    }
}