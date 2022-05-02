package com.andback.pocketfridge.present.utils

import android.util.Log

enum class Storage(val value: String) {
    Fridge("냉장"), Freeze("냉동"), Room("실온");

    companion object {
        private val map = values().associateBy(Storage::value)
        fun getWithString(string: String) = map[string]?: throw IllegalArgumentException()
    }

}