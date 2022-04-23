package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.model.BaseReponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.FieldMap

interface UserRemoteDataSource {
    fun signUp(@FieldMap req: MutableMap<String, String>): Flowable<BaseReponse<Any>>
}