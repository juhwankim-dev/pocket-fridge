package com.andback.pocketfridge.present.utils

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun toStringDate(year: Int, monthMinusOne: Int, day: Int): String {
        val month = monthMinusOne + 1
        val monthString = if(month < 10) "0$month" else month.toString()
        val dayString = if(day < 10) "0$day" else day.toString()
        return "$year-$monthString-$dayString"
    }

    fun toDate(stringDate: String): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        formatter.isLenient = true
        formatter.parse(stringDate)?.let {
            return it
        }?: throw Exception("date parsing error")
    }
}