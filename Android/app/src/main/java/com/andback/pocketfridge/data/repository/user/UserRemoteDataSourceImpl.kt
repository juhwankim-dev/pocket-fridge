package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.api.UserApi
import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Flowable
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : UserRemoteDataSource {
    override fun signUp(req: MutableMap<String, String>): Flowable<BaseResponse<Any>> {
        return userApi.signUp(req)
    }

    override fun sendEmail(email: String): Flowable<BaseResponse<String>> {
        return userApi.sendEmail(email)
    }

    override fun checkEmail(email: String): Flowable<BaseResponse<Any>> {
        return userApi.checkEmail(email)
    }

    override fun checkNickname(nickname: String): Flowable<BaseResponse<Any>> {
        return userApi.checkNickname(nickname)
    }
}