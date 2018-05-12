package com.khpi.classschedule.utils

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    fun getDateFromPicker(year: Int, month: Int, day: Int) : String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val date = calendar.time
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun getMillisFromPicker(year: Int, month: Int, day: Int) : Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND))
        return calendar.timeInMillis - 1000 * 60 * 60 * 24
    }

    fun getDateFromMillis(millis: Long) : String {
        val targetMillis = millis + 1000 * 60 * 60 * 24
        val date = Date()
        date.time = targetMillis
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }
}