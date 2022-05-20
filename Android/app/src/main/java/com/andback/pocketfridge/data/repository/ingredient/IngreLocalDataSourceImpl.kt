package com.andback.pocketfridge.data.repository.ingredient

import com.andback.pocketfridge.data.db.dao.IngreDao
import com.andback.pocketfridge.data.model.IngreEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class IngreLocalDataSourceImpl @Inject constructor(
    private val dao: IngreDao
): IngreLocalDataSource {
    override fun insert(ingreEntity: IngreEntity): Completable {
        return dao.insert(ingreEntity)
    }

    override fun insertAll(ingreList: List<IngreEntity>): Completable {
        return dao.insertAll(ingreList)
    }

    override fun deleteById(id: Int): Completable {
        val result = dao.deleteById(id)
        return if(result >= 0) Completable.complete()
            else Completable.error(Exception("delete fail"))
    }

    override fun getIngreListByDateBetween(from: String, to: String): Single<List<IngreEntity>> {
        return dao.getIngreListByDateBetween(from, to)
    }

    override fun getIngreListByFridgeId(id: Int): Single<List<IngreEntity>> {
        return dao.getIngreListByFridgeId(id)
    }

    override fun dropByFridgeId(id: Int): Completable {
        val result: Int = dao.dropByFridgeId(id)
        return if(result >= 0) Completable.complete()
            else Completable.error(Exception("delete fail"))
    }
}