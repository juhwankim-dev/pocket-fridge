package com.andback.pocketfridge.data.model

import com.google.gson.annotations.SerializedName

data class FridgeEntity(
    @SerializedName("refrigeratorId") val id: Int,
    @SerializedName("refrigeratorName") val name: String,
    @SerializedName("refrigeratorOwner") val isOwner: Boolean
)
