package com.andback.pocketfridge.data.db.dao

import androidx.room.*
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.present.config.INGRE_TABLE
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface IngreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingreEntity: IngreEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ingreList: List<IngreEntity>): Completable

    @Delete
    fun deleteById(id: Int): Completable

    @Transaction
    @Query("SELECT * FROM $INGRE_TABLE WHERE foodIngredientExp BETWEEN :from AND :to")
    fun getIngreListByDateBetween(from: String, to: String): Single<List<IngreEntity>>

    @Transaction
    @Query("SELECT * FROM $INGRE_TABLE WHERE refrigeratorId = :id")
    fun getIngreListByFridgeId(id: Int): Single<List<IngreEntity>>
}