package com.khpi.classschedule.data.models

data class BaseModel (
        val title: String?,
        val id: Int?,
        var course: Int?,
        var isPinned: Boolean = false,
        val parentName: String?,
        val scheduleType: ScheduleType?,
        var properties: MutableList<Property> = mutableListOf()
)

