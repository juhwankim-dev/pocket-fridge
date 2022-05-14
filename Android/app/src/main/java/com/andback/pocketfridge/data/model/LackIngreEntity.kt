package com.andback.pocketfridge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LackIngreEntity(
    @SerializedName("recipeIngredientName") val name: String
) : Parcelable
