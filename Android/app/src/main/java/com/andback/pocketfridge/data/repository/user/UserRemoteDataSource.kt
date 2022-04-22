package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.model.TempEntity
import io.reactivex.Single
import retrofit2.http.FieldMap

interface UserRemoteDataSource {
    fun signUp(@FieldMap req: MutableMap<String, String>): Single<TempEntity>
}