package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.api.UserApi
import com.andback.pocketfridge.data.model.BaseReponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : UserRemoteDataSource {
    override fun signUp(req: MutableMap<String, String>): Flowable<BaseReponse<Any>> {
        return userApi.signUp(req)
    }
}