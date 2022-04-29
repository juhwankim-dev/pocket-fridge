package com.andback.pocketfridge.data.repository.ingredient

import com.andback.pocketfridge.data.api.IngreApi
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class IngreRemoteDataSourceImpl @Inject constructor(val ingreApi: IngreApi): IngreRemoteDataSource {
    override fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Observable<BaseResponse<Any>> {
        return ingreApi.uploadIngre(ingreEntityForUpload)
    }

    override fun getIngreListByFridgeId(fridgeId: Int): Single<BaseResponse<List<IngreEntity>>> {
        return ingreApi.getIngreListByFridgeId(fridgeId)
    }
}