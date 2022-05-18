package com.andback.pocketfridge.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val allIngredients: String,
    val content: String,
    val foodName: String,
    var url: String?,
    val serving: String,
    val time: String,
    val difficulty: String,
    val type: String,
    var like: Boolean,
    var isRecommendation: Boolean
) : Parcelable {
    fun getTimeAndDifficulty(): String {
        return "${time}분 · $difficulty"
    }
}