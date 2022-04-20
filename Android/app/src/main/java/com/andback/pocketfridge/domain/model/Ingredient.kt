package com.andback.pocketfridge.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    var category: String,
    var quantity: Int = 0,
    var purchasedDate: String,
    var expiryDate: String,
    var name: String = "",
    var fridgeId: Int = -1,
    var storage: String) :
    Parcelable
