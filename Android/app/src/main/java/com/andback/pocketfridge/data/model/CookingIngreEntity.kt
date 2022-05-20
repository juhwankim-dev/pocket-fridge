package com.andback.pocketfridge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CookingIngreEntity(
    @SerializedName("recipeIngredientName") val name: String,
    @SerializedName("recipeIngredientCount") val count: String
) : Parcelable
