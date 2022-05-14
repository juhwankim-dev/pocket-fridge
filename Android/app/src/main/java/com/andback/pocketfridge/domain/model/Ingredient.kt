package com.andback.pocketfridge.domain.model

import android.os.Parcelable
import com.andback.pocketfridge.present.utils.Storage
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    var id: Int = -1,
    var subCategory: Int,
    var mainCategory: Int?,
    var quantity: Int = 0,
    var purchasedDate: String,
    var expiryDate: String,
    var name: String = "",
    var fridgeId: Int = -1,
    var storage: Storage,
    var leftDay: Int = 0) :
    Parcelable
