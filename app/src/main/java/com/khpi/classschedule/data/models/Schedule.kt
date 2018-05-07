package com.khpi.classschedule.data.models

import com.google.gson.annotations.SerializedName

data class FullSchedule(
        @SerializedName("Monday") val monday: HashMap<String, Schedule>?,
        @SerializedName("Tuesday") val tuesday: HashMap<String, Schedule>?,
        @SerializedName("Wednesday") val wednesday: HashMap<String, Schedule>?,
        @SerializedName("Thursday") val thursday: HashMap<String, Schedule>?,
        @SerializedName("Friday") val friday: HashMap<String, Schedule>?
)

data class Schedule(
        var time: String?,
        var couple: Int?,
        @SerializedName("Name") val name: String?,
        @SerializedName("Aud") val auditory: String?,
        @SerializedName("vid") val type: String?,
        @SerializedName("Prepod") val teacher: String?
)

data class BaseSchedule(
        val monday: List<Schedule>,
        val tuesday: List<Schedule>,
        val wednesday: List<Schedule>,
        val thursday: List<Schedule>,
        val friday: List<Schedule>
)