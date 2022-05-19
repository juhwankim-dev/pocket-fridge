package com.andback.pocketfridge.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubCategoryEntity(
    val mainCategoryId: Int,
    val subCategoryId: Int,
    val subCategoryName: String
) : Parcelable