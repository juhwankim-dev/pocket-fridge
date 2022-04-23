package com.andback.pocketfridge.data.model

data class IngreEntityForUpload(
    val name: String,
    val expiryDate: String,
    val purchasedDate: String,
    val fridgeId: Int,
    val quantity: Int = 1,
    val category: String
)