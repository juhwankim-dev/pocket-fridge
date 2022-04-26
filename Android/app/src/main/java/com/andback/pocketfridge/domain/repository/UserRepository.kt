package com.andback.pocketfridge.domain.repository

import com.andback.pocketfridge.data.model.BaseResponse
import io.reactivex.Observable

interface UserRepository {
    fun signUp(req: MutableMap<String, String>): Observable<BaseResponse<Any>>
    fun sendEmail(email: String): Observable<BaseResponse<String>>
    fun checkEmail(email: String): Observable<BaseResponse<Any>>
    fun checkNickname(nickname: String): Observable<BaseResponse<Any>>
}