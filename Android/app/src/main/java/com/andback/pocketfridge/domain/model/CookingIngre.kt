package com.andback.pocketfridge.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CookingIngre(
    @SerializedName("name") val name: String,
    @SerializedName("count") val count: String,
    @SerializedName("isLack") val isLack: Boolean
) : Parcelable

