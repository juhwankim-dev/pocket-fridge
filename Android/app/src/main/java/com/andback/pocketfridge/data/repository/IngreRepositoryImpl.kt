package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.mapper.IngreMapper
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSource
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.repository.IngreRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class IngreRepositoryImpl @Inject constructor(private val ingreRemoteDataSource: IngreRemoteDataSource): IngreRepository {
    // TODO: 매퍼 적용 필요 
    override fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Single<BaseResponse<Any>> {
        return ingreRemoteDataSource.uploadIngre(ingreEntityForUpload)
    }

    override fun getIngreListByFridgeId(fridgeId: Int): Single<BaseResponse<List<Ingredient>>> {
        val result = ingreRemoteDataSource.getIngreListByFridgeId(fridgeId)
        return result.map {
            val list = it.data?.map { entity ->
                IngreMapper(entity)
            }
            BaseResponse(message = it.message, status = it.status, data = list)
        }
    }

    override fun deleteIngreById(ingreId: Int): Single<BaseResponse<Any>> {
        return ingreRemoteDataSource.deleteIngreById(ingreId)
    }

    override fun updateIngre(ingreId: Int, ingredient: Ingredient): Single<BaseResponse<Any>> {
        val ingreEntity = IngreMapper.invoke(ingredient)
        return ingreRemoteDataSource.updateIngre(ingreId, ingreEntity)
    }
}