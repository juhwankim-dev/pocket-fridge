package com.andback.pocketfridge.data.repository

import com.andback.pocketfridge.data.model.TempEntity
import com.andback.pocketfridge.data.repository.user.UserRemoteDataSource
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun signUp(req: MutableMap<String, String>): Single<TempEntity> {
        return userRemoteDataSource.signUp(req)
    }
}