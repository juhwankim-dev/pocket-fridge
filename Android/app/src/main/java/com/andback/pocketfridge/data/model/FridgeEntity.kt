package com.andback.pocketfridge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FridgeEntity(
    @SerializedName("refrigeratorId") val id: Int,
    @SerializedName("refrigeratorName") val name: String,
    @SerializedName("refrigeratorOwner") val isOwner: Boolean
) : Parcelable
