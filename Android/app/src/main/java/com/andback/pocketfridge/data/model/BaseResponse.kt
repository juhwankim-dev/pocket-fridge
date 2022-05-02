package com.andback.pocketfridge.data.model

class BaseResponse<out T> (
    val message: String = "",
    val status: Int = -1,
    val data: T? = null
)