package com.khpi.classschedule.data.models

abstract class BaseModel{
    abstract var title: String?
    abstract val id: Int?
}

data class BaseSchedule (
        override var title: String?,
        override var id: Int?,
        var course: Int?,
        val isPinned: Boolean = false,
        val parentName: String?,
        val scheduleType: ScheduleType?
) : BaseModel()

