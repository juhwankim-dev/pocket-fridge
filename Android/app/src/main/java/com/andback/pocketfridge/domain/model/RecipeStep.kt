package com.andback.pocketfridge.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipeStep (
    val step: Int,
    val explanation: String,
    val url: String?
) : Parcelable