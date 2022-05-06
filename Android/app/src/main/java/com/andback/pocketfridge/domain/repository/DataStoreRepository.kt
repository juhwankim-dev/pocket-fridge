package com.andback.pocketfridge.domain.repository

interface DataStoreRepository {
    suspend fun readDataStore(key: String): String?
    suspend fun writeDataStore(key: String, value: String)
}