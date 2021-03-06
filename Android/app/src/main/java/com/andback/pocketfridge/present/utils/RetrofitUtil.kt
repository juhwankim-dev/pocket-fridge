package com.andback.pocketfridge.present.utils

import com.andback.pocketfridge.data.api.*
import com.andback.pocketfridge.present.config.ApplicationClass

class RetrofitUtil {
    companion object {
        val userService = ApplicationClass.retrofit.create(UserApi::class.java)
        val ingreService = ApplicationClass.retrofit.create(IngreApi::class.java)
        val fridgeService = ApplicationClass.retrofit.create(FridgeApi::class.java)
        val categoryService = ApplicationClass.retrofit.create(CategoryApi::class.java)
        val recipeService = ApplicationClass.retrofit.create(RecipeApi::class.java)
        val barcodeService = ApplicationClass.barcodeRetrofit.create(BarcodeApi::class.java)
        val likeService = ApplicationClass.retrofit.create(LikeApi::class.java)
        val notificationService = ApplicationClass.retrofit.create(NotificationApi::class.java)
    }
}