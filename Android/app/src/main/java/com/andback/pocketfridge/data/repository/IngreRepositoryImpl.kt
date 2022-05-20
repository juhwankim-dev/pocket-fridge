package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.mapper.IngreMapper
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.data.repository.ingredient.IngreLocalDataSource
import com.andback.pocketfridge.data.repository.ingredient.IngreRemoteDataSource
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.repository.IngreRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

class IngreRepositoryImpl @Inject constructor(
    private val ingreRemoteDataSource: IngreRemoteDataSource,
    private val ingreLocalDataSource: IngreLocalDataSource
): IngreRepository {
    // TODO: 매퍼 적용 필요 
    override fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Single<BaseResponse<Any>> {
        return ingreRemoteDataSource.uploadIngre(ingreEntityForUpload)
    }

    /**
     * 1.서버에 해당 냉장고의 식재료를 요청하면
     * -> 성공
     *      2.localDataSource로 db 객체 삭제
     *      -> 성공
     *          3.네트워크로 받은 리스트 db에 insert
     *          -> 성공
     *              list를 갖는 Single 반환
     *          -> 실패
     *              에러처리
     *      -> 실패
     *          에러 처리
     * -> 실패
     *  에러 처리
     */
    override fun getIngreListByFridgeId(fridgeId: Int): Single<BaseResponse<List<Ingredient>>> {
        var listFromServer: List<IngreEntity> = emptyList()
        var dtoList: List<Ingredient> = emptyList()

        val result = ingreRemoteDataSource.getIngreListByFridgeId(fridgeId)
            .doOnSuccess { response ->
                if(response.data != null) {
                    listFromServer = response.data
                    dtoList = listFromServer.map { entity -> IngreMapper(entity) }
                } else {
                    throw Exception("get null from server")
                }
            }.map {
                // completable의 완료를 기다린 후 결과는 Single 객체로 반환
                ingreLocalDataSource.dropByFridgeId(fridgeId)
                    .andThen(Single.just(true))
            }.flatMap {
                ingreLocalDataSource.insertAll(listFromServer).andThen(Single.just(dtoList))
            }.flatMap {
                ingreLocalDataSource.getIngreListByFridgeId(fridgeId)
            }.flatMap {
                Single.just(BaseResponse(data = it.map { ingreEntity -> IngreMapper(ingreEntity) }))
            }

        return result
    }

    override fun getAllIngreList(): Single<BaseResponse<List<Ingredient>>> {
        var dtoList: List<Ingredient> = emptyList()

        val result = ingreRemoteDataSource.getAllIngreList()
            .doOnSuccess { response ->
                if(response.data != null) {
                    dtoList = response.data.map { entity -> IngreMapper(entity) }
                } else {
                    throw Exception("get null from server")
                }
            }.map {
                BaseResponse(data = dtoList)
            }

        return result
    }

    override fun deleteIngreById(ingreId: Int): Single<BaseResponse<Any>> {
        return ingreRemoteDataSource.deleteIngreById(ingreId)
    }

    override fun updateIngre(ingredient: Ingredient): Single<BaseResponse<Any>> {
        val ingreEntity = IngreMapper.invoke(ingredient)
        return ingreRemoteDataSource.updateIngre(ingreEntity)
    }

    override fun getIngreListByDateBetween(from: String, to: String): Single<List<Ingredient>> {
        return ingreLocalDataSource.getIngreListByDateBetween(from, to).map { list ->
            list.map { IngreMapper(it) }
        }
    }
}