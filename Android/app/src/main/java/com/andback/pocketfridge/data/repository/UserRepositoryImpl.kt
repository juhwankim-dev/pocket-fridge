package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.*
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSource
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun getUser(): Single<BaseResponse<UserEntity>> {
        return userRemoteDataSource.getUser()
    }

    override fun signUp(signUpEntity: SignUpEntity): Single<BaseResponse<Any>> {
        return userRemoteDataSource.signUp(signUpEntity)
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

    override fun login(loginEntity: LoginEntity): Single<BaseResponse<String>> {
        return userRemoteDataSource.login(loginEntity)
    }
}