package com.andback.pocketfridge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserEntity(
    @SerializedName("userEmail") val email: String,
    @SerializedName("userName") val name: String,
    @SerializedName("userNickname") val nickname: String,
    @SerializedName("userPassword") val pw: String,
    @SerializedName("userPicture") val picture: String?,
) : Parcelable
