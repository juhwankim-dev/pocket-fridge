package com.andback.pocketfridge.data.model

data class NotificationEntity(
    val notificationId: Int,
    val notificationMessage: String,
    val notificationRead: Boolean,
    val refrigeratorId: Int
)
