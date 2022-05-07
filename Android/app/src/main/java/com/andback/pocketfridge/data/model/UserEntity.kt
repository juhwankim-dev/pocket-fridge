package com.andback.pocketfridge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    @SerializedName("userId") val id: Int,
    @SerializedName("userEmail") val email: String,
    @SerializedName("userName") val name: String,
    @SerializedName("userNickname") val nickname: String,
    @SerializedName("userPicture") val picture: String?,
) : Parcelable
