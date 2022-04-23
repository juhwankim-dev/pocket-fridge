package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.BaseReponse
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSource
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun signUp(req: MutableMap<String, String>): Flowable<BaseReponse<Any>> {
        return userRemoteDataSource.signUp(req)
    }
}