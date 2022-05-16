package com.andback.pocketfridge.data.model

import com.google.gson.annotations.SerializedName

data class ShareUserEntity(
    @SerializedName("pictureUrl") val pic: String,
    @SerializedName("userNickname") val nickname: String,
    @SerializedName("userEmail") val email: String,
    @SerializedName("owner") val isOwner: Boolean
)
