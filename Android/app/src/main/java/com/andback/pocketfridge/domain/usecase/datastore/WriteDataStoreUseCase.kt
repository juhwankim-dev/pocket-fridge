package com.andback.pocketfridge.domain.usecase.datastore

import com.andback.pocketfridge.domain.repository.DataStoreRepository
import javax.inject.Inject

class WriteDataStoreUseCase @Inject constructor(private val repository: DataStoreRepository) {
    suspend fun execute(key: String, value: String) = repository.writeDataStore(key, value)
}