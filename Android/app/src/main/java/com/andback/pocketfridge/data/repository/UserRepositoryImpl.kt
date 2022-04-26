package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSource
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun signUp(req: MutableMap<String, String>): Flowable<BaseResponse<Any>> {
        return userRemoteDataSource.signUp(req)
    }

    override fun sendEmail(email: String): Flowable<BaseResponse<String>> {
        return userRemoteDataSource.sendEmail(email)
    }

    override fun checkEmail(email: String): Flowable<BaseResponse<Any>> {
        return userRemoteDataSource.checkEmail(email)
    }

    override fun checkNickname(nickname: String): Flowable<BaseResponse<Any>> {
        return userRemoteDataSource.checkNickname(nickname)
    }
}