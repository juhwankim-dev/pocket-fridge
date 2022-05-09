package com.andback.pocketfridge.domain.infra

interface IngreExpiryNotiManager {
    fun sendNoti(title: String, body: String)
}