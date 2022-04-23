package com.andback.pocketfridge.data.repository.ingredient

import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.data.model.TempEntity
import io.reactivex.Single

interface IngreRemoteDataSource {
    fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Single<TempEntity>
}