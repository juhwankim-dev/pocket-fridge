package com.andback.pocketfridge.present.utils

object DateConverter {
    fun toStringDate(year: Int, monthMinusOne: Int, day: Int): String {
        val month = monthMinusOne + 1
        val monthString = if(month < 10) "0$month" else month.toString()
        val dayString = if(day < 10) "0$day" else day.toString()
        return "$year-$monthString-$dayString"
    }
}