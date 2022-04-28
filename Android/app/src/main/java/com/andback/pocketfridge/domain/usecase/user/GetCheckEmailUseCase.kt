package com.andback.pocketfridge.domain.usecase.user

import android.util.Log
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.repository.UserRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetCheckEmailUseCase @Inject constructor(val repository: UserRepository) {
    fun execute(email: String): Single<BaseResponse<Any>> = repository.checkEmail(email)
}