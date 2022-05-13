package com.andback.pocketfridge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Password(
    @SerializedName("userPassword") val password: String
) : Parcelable
