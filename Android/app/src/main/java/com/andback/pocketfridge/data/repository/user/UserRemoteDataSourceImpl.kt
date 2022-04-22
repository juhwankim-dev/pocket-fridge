package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.api.UserApi
import com.andback.pocketfridge.data.model.TempEntity
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : UserRemoteDataSource {
    override fun signUp(req: MutableMap<String, String>): Single<TempEntity> {
        return userApi.signUp(req)
    }
}