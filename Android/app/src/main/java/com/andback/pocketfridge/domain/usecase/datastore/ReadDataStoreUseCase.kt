package com.andback.pocketfridge.domain.usecase.datastore

import com.andback.pocketfridge.domain.repository.DataStoreRepository
import javax.inject.Inject

class ReadDataStoreUseCase @Inject constructor(val repository: DataStoreRepository) {
    suspend fun execute(key: String) = repository.readDataStore(key)
}