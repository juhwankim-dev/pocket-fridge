package com.andback.pocketfridge.present.utils

import com.andback.pocketfridge.data.api.IngreApi
import com.andback.pocketfridge.data.api.UserApi
import com.andback.pocketfridge.present.config.ApplicationClass

class RetrofitUtil {
    companion object {
        val userService = ApplicationClass.retrofit.create(UserApi::class.java)
        val ingreService = ApplicationClass.retrofit.create(IngreApi::class.java)
    }
}