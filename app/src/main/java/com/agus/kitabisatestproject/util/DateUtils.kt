package com.agus.kitabisatestproject.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    @JvmStatic
    fun format(date: Date?, format: String): String {
        val datFormatter = SimpleDateFormat(format, Locale.getDefault())
        datFormatter.timeZone = TimeZone.getDefault()
        return datFormatter.format(date)
    }

    @JvmStatic
    fun parseUTCTime(time: String): Date? {
        val dateParser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dateParser.timeZone = TimeZone.getTimeZone("UTC")
        return dateParser.parse(time)
    }
}
