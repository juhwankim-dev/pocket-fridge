package com.andback.pocketfridge.data.repository.user

import com.andback.pocketfridge.data.api.UserApi
import com.andback.pocketfridge.data.model.*
import io.reactivex.Single
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userApi: UserApi) : UserRemoteDataSource {
    override fun getUser(): Single<BaseResponse<UserEntity>> {
        return userApi.getUser()
    }

    override fun signUp(signUpEntity: SignUpEntity): Single<BaseResponse<Any>> {
        return userApi.signUp(signUpEntity)
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

    override fun findPW(userForFind: UserForFindEntity): Single<BaseResponse<Any>> {
        return userApi.findPW(userForFind)
    }

    override fun login(loginEntity: LoginEntity): Single<BaseResponse<String>> {
        return userApi.login(loginEntity)
    }
}