package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.IngreEntityForUpload
import com.andback.pocketfridge.data.model.TempEntity
import io.reactivex.Single

interface IngreRepository {
    fun uploadIngre(ingreEntityForUpload: IngreEntityForUpload): Single<TempEntity>
}