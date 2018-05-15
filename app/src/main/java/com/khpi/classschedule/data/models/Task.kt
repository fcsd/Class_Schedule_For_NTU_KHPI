package com.khpi.classschedule.data.models

enum class CoupleType(val title: String) {
    LECTURE("Лекція"),
    LABORATORY("Лабораторна"),
    PRACTICE("Практика"),
    SEMINAR("Семінар")
}

data class Task(
        val id: Int,
        val group: String,
        val subject: String,
        val type: CoupleType,
        val notificationTime: Long,
        val description: String,
        var properties: MutableList<Property> = mutableListOf()
)

enum class TaskSort {
    DATE,
    GROUP,
    SUBJECT
}

enum class TaskRemove(val title: String, val timeMillis: Long) {
    NOT_REMOVE("Не видаляти", 0),
    ONR_HOUR("Через час", 3600000),
    ONE_DAY("Через день", 86400000),
    THREE_DAYS("Через три дня", 259200000),
    ONE_WEEK("Через тиждень", 604800000)

}