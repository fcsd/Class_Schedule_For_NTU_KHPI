package com.khpi.classschedule.data.models

enum class CoupleType(val title: String, val position: Int) {
    LECTURE("Лекція", 0),
    LABORATORY("Лабораторна", 1),
    PRACTICE("Практика", 2),
    SEMINAR("Семінар", 3)
}

data class Task(
        val id: Int,
        val group: String,
        val subject: String,
        val type: CoupleType,
        val notificationTime: Long,
        val description: String
)

enum class TaskSort {
    DATE,
    GROUP,
    SUBJECT
}