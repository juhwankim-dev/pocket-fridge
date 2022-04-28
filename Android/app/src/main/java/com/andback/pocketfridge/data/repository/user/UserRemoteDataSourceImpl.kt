package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.api.UserApi
import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : UserRemoteDataSource {
    override fun signUp(req: MutableMap<String, String>): Single<BaseResponse<Any>> {
        return userApi.signUp(req)
    }

    override fun sendEmail(email: String): Single<BaseResponse<String>> {
        return userApi.sendEmail(email)
    }

    override fun checkEmail(email: String): Single<BaseResponse<Any>> {
        return userApi.checkEmail(email)
    }

    override fun checkNickname(nickname: String): Single<BaseResponse<Any>> {
        return userApi.checkNickname(nickname)
    }

    override fun login(req: MutableMap<String, String>): Single<BaseResponse<String>> {
        return userApi.login(req)
    }
}