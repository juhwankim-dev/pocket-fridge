package com.andback.pocketfridge.domain.usecase.datastore

import com.andback.pocketfridge.domain.repository.DataStoreRepository
import javax.inject.Inject

class ResetDataStoreUseCase @Inject constructor(val repository: DataStoreRepository) {
    suspend fun execute() = repository.resetDataStore()
}