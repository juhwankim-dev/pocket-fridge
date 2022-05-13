package com.andback.pocketfridge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token (
    @SerializedName("jwtToken") val token: String,
    @SerializedName("loginType") val loginType: String
) : Parcelable