package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Flowable
import retrofit2.http.FieldMap

interface UserRemoteDataSource {
    fun signUp(@FieldMap req: MutableMap<String, String>): Flowable<BaseResponse<Any>>
}