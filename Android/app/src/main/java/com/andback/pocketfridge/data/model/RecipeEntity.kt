package com.andback.pocketfridge.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeEntity (
    @SerializedName("recipeId") val id: Int,
    @SerializedName("recipeAllIngredient") val allIngredients: String,
    @SerializedName("recipeContent") val content: String,
    @SerializedName("recipeFoodName") val foodName: String,
    @SerializedName("recipeImage") val url: String?,
    @SerializedName("recipeServing") val serving: String,
    @SerializedName("recipeTime") val time: String,
    @SerializedName("recipeType") val type: String
): Parcelable