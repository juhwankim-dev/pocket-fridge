package com.andback.pocketfridge.data.repository.ingredient

import com.andback.pocketfridge.data.model.IngreEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IngreLocalDataSource {
    fun insert(ingreEntity: IngreEntity): Completable
    fun insertAll(ingreList: List<IngreEntity>): Completable
    fun deleteById(id: Int): Completable
    fun getIngreListByDateBetween(from: String, to: String): Single<List<IngreEntity>>
    fun getIngreListByFridgeId(id: Int): Single<List<IngreEntity>>
}