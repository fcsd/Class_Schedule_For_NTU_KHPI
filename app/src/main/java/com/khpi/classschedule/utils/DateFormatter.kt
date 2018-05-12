package com.khpi.classschedule.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    fun formatShownDateFromPicker(year: Int, month: Int, day: Int) : String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val date = calendar.time
        return DateFormat.getDateInstance(SimpleDateFormat.LONG, Locale("uk", "UA")).format(date)
    }

    fun getNotificationMillisFromPicker(year: Int, month: Int, day: Int) : Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND))
        return calendar.timeInMillis - 1000 * 60 * 60 * 24
    }
}