package com.andback.pocketfridge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginEntity (
    @SerializedName("userEmail") val email: String,
    @SerializedName("userPassword") val pw: String
) : Parcelable