package com.khpi.classschedule.data.models

data class BaseModel (
        var title: String?,
        val id: Int?,
        var course: Int?,
        val isPinned: Boolean = false,
        val parentName: String?,
        val scheduleType: ScheduleType?
)

