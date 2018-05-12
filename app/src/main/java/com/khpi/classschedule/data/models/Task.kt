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
        val description: String
)