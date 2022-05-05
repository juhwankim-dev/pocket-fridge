package com.andback.pocketfridge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andback.pocketfridge.data.db.dao.IngreDao
import com.andback.pocketfridge.data.model.IngreEntity

@Database(entities = [IngreEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ingreDao(): IngreDao
}