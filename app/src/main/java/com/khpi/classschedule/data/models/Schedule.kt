package com.khpi.classschedule.data.models

import com.google.gson.annotations.SerializedName

data class FullSchedule(
        @SerializedName("Monday") val monday: HashMap<String, ScheduleItem>?,
        @SerializedName("Tuesday") val tuesday: HashMap<String, ScheduleItem>?,
        @SerializedName("Wednesday") val wednesday: HashMap<String, ScheduleItem>?,
        @SerializedName("Thursday") val thursday: HashMap<String, ScheduleItem>?,
        @SerializedName("Friday") val friday: HashMap<String, ScheduleItem>?
)

data class ScheduleItem(
        var time: String?,
        var couple: Int?,
        @SerializedName("Name") val name: String?,
        @SerializedName("Aud") val auditory: String?,
        @SerializedName("vid") val type: String?,
        @SerializedName("Prepod") val teacher: String?
)

data class Schedule(
        val monday: List<ScheduleItem>,
        val tuesday: List<ScheduleItem>,
        val wednesday: List<ScheduleItem>,
        val thursday: List<ScheduleItem>,
        val friday: List<ScheduleItem>
)

enum class ScheduleType {
    GROUP,
    TEACHER,
    AUDITORY
}