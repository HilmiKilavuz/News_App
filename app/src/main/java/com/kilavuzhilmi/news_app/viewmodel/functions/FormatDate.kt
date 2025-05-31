package com.kilavuzhilmi.news_app.viewmodel.functions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object FormatDate {
    fun formatDate(input: String?): String {
        return try {
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val date = parser.parse(input ?: "")
            val formatter = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
            formatter.format(date ?: Date())
        } catch (e: Exception) {
            "Tarih Yok"
        }
    }
}