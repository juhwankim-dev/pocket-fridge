package com.andback.pocketfridge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeStepEntity(
    @SerializedName("recipeProcessSequence") val step: Int,
    @SerializedName("recipeProcessDescription") val explanation: Int,
    @SerializedName("recipeProcessImage") val url: Int
) : Parcelable
