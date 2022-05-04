package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.UserForFindEntity
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSource
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun signUp(req: MutableMap<String, String>): Single<BaseResponse<Any>> {
        return userRemoteDataSource.signUp(req)
    }

    override fun sendEmail(email: String): Single<BaseResponse<String>> {
        return userRemoteDataSource.sendEmail(email)
    }

    override fun checkEmail(email: String): Single<BaseResponse<Any>> {
        return userRemoteDataSource.checkEmail(email)
    }

    override fun checkNickname(nickname: String): Single<BaseResponse<Any>> {
        return userRemoteDataSource.checkNickname(nickname)
    }

    override fun findPW(userForFind: UserForFindEntity): Single<BaseResponse<Any>> {
        return userRemoteDataSource.findPW(userForFind)
    }

    override fun login(req: MutableMap<String, String>): Single<BaseResponse<String>> {
        return userRemoteDataSource.login(req)
    }
}