package com.khpi.classschedule.data.models

data class Property(
        var title: String,
        var image: Int,
        var type: PropertyType
)

enum class PropertyType {
    TASK_ADD,
    TASK_SHOW,
    BUILDING,
    REMOVE
}